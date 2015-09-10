I've identified that a considerable time has gone by since the last update. I'm going to put some adequate time into resolving that this month and into the future. I've been sitting on a couple of topics that I want to dig into (regarding how I handle [email](http://notmuchmail.org) and some work I've been doing with [Clojure](http://clojure.org)).

In the interim, I'll leave you with a little anecdote/tale of caution.

---

My wife and I (oh yeah, by the way, I got married in April. I really need to work on updates.) frequently play video games together to pass the time. I'm not normally terribly competitive, and she's not into the traditional multiplayer model of games, so I try to look for games we can play that are multiplayer co-op, or more turn-based (I've gotten us both hopelessly addicted to [Civilization](http://www.civilization.com/)).

Recently, with a lapse in new ideas for games, we bought her a copy of [Borderlands 2 on Steam](http://store.steampowered.com/sub/32848/) so that we could play through the storyline together. I had played shortly after it came out, and with friends both in the Midwest and in the PNW, so I had a number of game saves on my desktop.

*Complication #1*: When we play games, normally Jordan gets the desktop, so that she has a better experience with the game. Having already played it, I get relegated to my laptop (running integrated Intel graphics) when I play along.

*Complication #2*:  The group that did the porting of the game to Linux ([Aspyr Media](http://www.aspyr.com/), who should get amazing credit for their work in extending video games to the Linux world) does not allow for Steam Cloud saves to cross platforms. As such, I had no access to the work I've put into the game previously.

---

We come to the events of tonight.

Jordan is away on a work trip, and Christopher is home alone. Christopher decides, "Hey, it's been forever since I've actually gotten to use my desktop for games, and I've been playing BL2 recently; let's play that."

An hour passes as Christopher enjoys playing one of his old characters, which is close in level to where he's at with Jordan.

Christopher: "Hey - I should see if I can copy over the character data to the laptop, so I have the option to play with those characters when Jordan gets back."

After researching, Christopher finds that the character data is saved in files called `save<nnnn>.sav`. Easy enough to copy from one location to the next.

Christopher copies the save data to a shared filesystem so he can access it on his laptop, and then fires off the following command:

```bash
cp save*.sav ~/.local/share/aspyr-media/borderlands 2/willowgame/savedata/<numerical id>/
```

After thinking about this, Christopher realizes in horror that *he's just saved over his character data he's been playing with his wife for the majority of the last week*.

Because the `cp` command doesn't default the `-i/--interactive` option, which would prompt on overwrite, it is easy to fall into this trap. In my case, both game saves were named "save0001.sav", and the `cp` call replaced my character data with the old one.

I scoured the Internet for methods of data recovery (there's a [really](http://superuser.com/questions/211301/recovery-of-overwritten-file) [cool](http://unix.stackexchange.com/a/150423) [tangent](http://unix.stackexchange.com/questions/101237/how-to-recover-files-i-deleted-now-by-running-rm) to take here on how mv/rm/cp actually behave), and most things I found referred to using text contained in the file to find the details. That'd be great, but the data files for those characters are [encrypted](http://gaming.stackexchange.com/a/84732).

Fortunately (for me), this story has a happy ending. Borderlands 2 has support for [Steam Cloud](https://en.wikipedia.org/wiki/Steam_%28software%29#cite_ref-39). As a last-ditch effort, I removed *all* save data from the folder on the laptop, and restarted the game. Upon loading, it detected a game save within Steam Cloud that didn't exist locally, and happily copied that save down to the local machine. I'm even more pleased to say that it was a recent save to, so I didn't lose my place!

---

So, the moral of today's story: make sure you use (or better yet, alias) cp to `cp -i`, so that when you write to a directory, you pause before overwriting.
