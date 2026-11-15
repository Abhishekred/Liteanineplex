# Changelog

Only the current version is kept here: each `vX.Y.Z` tag publishes the TV and Android APKs, and this section becomes the release notes.

## 2.23.0

### ✨ New
- **New and Popular in every language.** On Movies and TV, each language chip (Hindi, Tamil, Telugu...) opens on a **New** row (every release of the last year, newest first) and a **Popular** row, then the whole list. Refreshed every day.
- **DASH downloads**, and HLS streams that keep all their pieces in one file.
- **Downloads are MP4 files you can seek in**: streams made of `.ts` pieces are converted when they finish. Old `.ts` downloads get a **Make seekable** button on the Downloads screen.
- **Search on a TV runs when you press Enter**, so the page stays still while you type with the remote; the remote then lands on the first result.

### 🔧 Changed
- **Zoom is Fit, Crop and Stretch.** Crop fills the screen without squashing the picture and also removes black bars that are part of the video; Stretch fills it by stretching. 16:9 and 4:3 are gone.
- On a series, pressing a site only picks it; **Watch carries on where you stopped** (the next episode after one you finished) instead of starting at episode 1 (#22). On a movie, pressing a site still plays it.
- In the player on a TV, picking a quality, server, audio or speed takes you back to the video instead of the buttons.
- New replaces Trending on platform and brand pages.
- A stream that cannot be saved (no sound, live, protected) says why as soon as you press Download.
- DASH downloads take the sound in your audio language, never an audio description.

### 🐛 Fixed
- The loading spinner turns on TVs with animations switched off; it stood still and looked frozen.
- Downloaded movies with no seek bar or a LIVE label (#23).
- Picture and sound of some downloads failed to join with no reason given.
- Crop looked like Stretch on some TVs.
- Downloads no longer need twice the free space, and a big on-demand download no longer runs out of memory on small TVs.
- Movies and TV no longer open with the remote on the second row.
- On a phone, the Home banner picture no longer spills over the row under it.
