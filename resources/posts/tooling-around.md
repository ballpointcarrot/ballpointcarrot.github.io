
So, I traded in my work laptop today to replace it with a new one. I'm glad I made the switch, as there were things that
I didn't need on the old one, and I didn't want to have to mess with a major upgrade path and the inevitable breaks that
were to come with it. Additionally, my workflow and suite of tools works better with the new hardware (I've switched to
an Ubuntu laptop from an OSX one), and it will centralize my daily work (email and communication, scheduling and
planning) around the laptop instead of the desktop, which can then be devoted to other tasks (like running development
projects).

Because of the switch, it got me to thinking about the tooling that I use on a daily basis, and how to most smoothly
move into getting that installed on a new system. Replacing hardware is tough work for a developer/engineer, and that
would be time otherwise spent working on problems or creating new things. I'm going to provide a rundown of my toolchain
and how I orchestrate the installation of the tools.

Note that, as this is tied to Linux, the tools will obviously be Linux-based. There are different flows for other OSs;
each ecosystem operates differently. This is my solution for myself; YMMV.

## Communication ##

Being able to send and receive messages is key. Ten minutes after receiving my laptop today, I had a meeting that I had
to run to, and to send an email containing some details to one of the attendees. I run my email from the office Exchange server to my desktop (and now the laptop) using
[fetchmail](http://fetchmail.berlios.de/) and [procmail](http://www.procmail.org/). While these may seem esoteric, and
not terribly user-friendly, it gives me a strength that no other email client has - I can run scripts around my
email. Procmail handles the filtering of the incoming fetchmail setup, and separates the email into the various folders
for easy filtering rules (based on sender, topic in subject, etc.). Emails sent specifically to me in the To: header get
a bit more prevalence, and emails with calendar invites as attachments get the attachment parsed and sent to a
calendar. Since everything was using a standard Maildir format for storage on the laptop, migrating the email was as
easy as running rsync between the two systems.

This covers only the receipt of email, not sending. Since December (referencing
[an earlier post](http://www.ballpointcarrot.net/blog/2014/02/07/learning-a-new-editor/)), I've been using Emacs to do
more and more things - this has grown to include IRC chat (via the [ERC](http://www.emacswiki.org/wiki/ERC) plugin) and
email (using a pair of utilities called [mu and mu4e](http://www.djcbsoftware.nl/code/mu/mu4e.html)). While normally I'd
entertain jokes here (like "emacs is a good OS, it just needs a decent text editor."), I have to say that it's handy to
have a single place to go for all the work you need to do. mu is external to Emacs, and is a search engine over your
Maildir folders; you can use it on the command line to pick out emails that pertain to certain topics, and filter them
by specific recipients and whether they're new/flagged/unread/etc. Mu4e wraps that logic in an easy to use mail client
from within Emacs, and it's been much nicer than sifting through Outlook.

## Environment ##

So I've got some software... now I have to customize what I run it in. Rather than stay with Unity (the default Ubuntu
installed WM, which I frankly feel is counter-intuitive), I choose to go to a lesser known window manager called
["Awesome."](http://awesome.naquadah.org/) Appropriately named, as I haven't left it since I found it. By using a tiling
window manager, I don't have to worry about resizing things to fit. Additionally, the entire interface is programmable
via Lua, so defining custom shortcut keys and startup programs is fairly simple:
```lua
    -- ...
    -- Hotkeys for handling window resizing and management:
    clientkeys = awful.util.table.join(
    awful.key({ modkey,           }, "f",      function (c) c.fullscreen = not c.fullscreen  end),
    awful.key({ modkey,           }, "q",      function (c) c:kill()                         end),
    awful.key({ modkey, "Control" }, "space",  awful.client.floating.toggle                     ),
    awful.key({ modkey, "Control" }, "Return", function (c) c:swap(awful.client.getmaster()) end),
    awful.key({ modkey,           }, "o",      awful.client.movetoscreen                        ),
    awful.key({ modkey, "Shift"   }, "r",      function (c) c:redraw()                       end),
    awful.key({ modkey,           }, "t",      function (c) c.ontop = not c.ontop            end),
    awful.key({ modkey,           }, "n",
        function (c)
            -- The client currently has the input focus, so it cannot be
            -- minimized, since minimized clients can't have the focus.
            c.minimized = true
        end),
    awful.key({ modkey,           }, "m",
        function (c)
            c.maximized_horizontal = not c.maximized_horizontal
            c.maximized_vertical   = not c.maximized_vertical
        end)
    )
    -- Autostarts
    awful.util.spawn_with_shell("/usr/bin/startup-script.sh")
    awful.util.spawn_with_shell("~/bin/runme")
```
Awesome's libraries have amusing names: "awful", "naughty", and "vicious", to name a few. Currently, I have this
attached to the GNOME session layer, so I get the benefit of GNOME's background and icon management, but the window
management of awesome.

## Code ##

A programmer can't do much unless he has languages to write in. For me, that means having to add some additional
packages:

* [rbenv](https://github.com/sstephenson/rbenv) - this manages various installations of Ruby and the ecosystem around
   it. Similar to RVM, this uses shim binaries to point to rotating Ruby versions, so that I can use one version with
   one app, and use a legacy version to work on an older one.

* [nvm](https://github.com/creationix/nvm) - Same idea as rbenv, but for node.js. These libraries give me the
   flexibility of using different runtimes for different applications, and makes it easier to install the languages than
   with the system-provided binaries (which can either be out of date or harder to manage).

* I've already mentioned it, but Emacs is in here, too. Being able to easily see language and typographical issues with
   flycheck, syntax highlighting for languages available via [el-get](https://github.com/dimitri/el-get), and being able
   to launch a simple shell within the editor to run a test is a blessing to getting things done.

## Tomorrow ##

This was just for the first day, too. Things like browser plugins (which we use a myriad of for work) will likely come
soon enough, and there will be tweaks to all of the stuff listed as what I need in the environment changes. Let me know
if you have any questions on any of this, and I'll do what I can to answer them.
