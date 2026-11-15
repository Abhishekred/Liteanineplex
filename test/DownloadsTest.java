// SDK=~/Library/Android/sdk; json.jar = org.json from maven; LIBS = libs/*.jar:
// javac -nowarn -implicit:none -cp "json.jar:$LIBS$SDK/platforms/android-34/android.jar" -sourcepath src:build -d /tmp/dltest src/me/champdev/weblock/Downloads.java test/DownloadsTest.java && java -cp /tmp/dltest:json.jar me.champdev.weblock.DownloadsTest
package me.champdev.weblock;

public class DownloadsTest {
    static int fails;

    static void check(String name, Object got, Object want) {
        if (!String.valueOf(got).equals(String.valueOf(want))) { fails++; System.out.println("FAIL " + name + ": got " + got + " want " + want); }
    }

    public static void main(String[] a) {
        long[] last = {Long.MIN_VALUE, Long.MIN_VALUE}, shift = {0, 0};
        long t = 0;
        // a smooth stream is left alone, B-frame order included
        for (long raw : new long[]{100_000_000L, 100_040_000L, 100_120_000L, 100_080_000L}) { t = Downloads.Timeline.on(raw, last, shift, 0); last[0] = Math.max(last[0], t); }
        check("smooth stays", t, 100_080_000L);
        // the times start over from 0 (a joined segment): carried on from the latest, not back to 0
        t = Downloads.Timeline.on(0, last, shift, 0);
        check("reset goes on", t, 100_120_000L + Downloads.Timeline.STEP);
        last[0] = t;
        t = Downloads.Timeline.on(40_000, last, shift, 0);
        check("and keeps going", t, 100_120_000L + Downloads.Timeline.STEP + 40_000);
        // the sound has its own shift
        long s = Downloads.Timeline.on(100_000_000L, last, shift, 1);
        check("sound unaffected at its start", s, 100_000_000L);
        // a jump forward is a reset too
        last[1] = s;
        s = Downloads.Timeline.on(200_000_000L, last, shift, 1);
        check("a jump forward closes up", s, 100_000_000L + Downloads.Timeline.STEP);
        // the best variant is the highest one whose sound an MP4 can hold
        String master = "#EXTM3U\n#EXT-X-STREAM-INF:BANDWIDTH=2000000,CODECS=\"avc1.640020,mp4a.40.2\",RESOLUTION=960x540,AUDIO=\"aud1\"\nlow.m3u8\n"
            + "#EXT-X-STREAM-INF:BANDWIDTH=8000000,CODECS=\"avc1.64002a,mp4a.40.2\",RESOLUTION=1920x1080,AUDIO=\"aud1\"\nhigh.m3u8\n"
            + "#EXT-X-STREAM-INF:BANDWIDTH=8200000,CODECS=\"avc1.64002a,ac-3\",RESOLUTION=1920x1080,AUDIO=\"aud2\"\nac3.m3u8\n"
            + "#EXT-X-STREAM-INF:BANDWIDTH=8300000,CODECS=\"avc1.64002a,ec-3\",RESOLUTION=1920x1080,AUDIO=\"aud3\"\nec3.m3u8\n";
        check("AAC variant over a higher AC-3 one", java.util.Arrays.toString(Downloads.Hls.bestVariant(master)), "[high.m3u8, 1080, aud1]");
        check("only AC-3: still the highest", java.util.Arrays.toString(Downloads.Hls.bestVariant(master.replace("mp4a.40.2", "ac-3"))), "[ec3.m3u8, 1080, aud3]");
        check("no variants", Downloads.Hls.bestVariant("#EXTM3U\n"), null);
        System.out.println(fails == 0 ? "all passed" : fails + " failed");
        if (fails > 0) System.exit(1);
    }
}
