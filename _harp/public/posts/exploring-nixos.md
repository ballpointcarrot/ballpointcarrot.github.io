Late-ish last year (around November, if memory serves), I read about a new OS project which 
allowed its users a stark departure from traditional Linux methods. Called [Nixos](http://nixos.org), 
the unique claim that it brought to the table was that the system configuration was managed declaratively, through
functional configuration files. This includes things like package management, services executed, and user configuration.

To fit along with my foray into the world of functional programming (my [jumps](/posts/rust-non-systems-programmer) into [Rust](/posts/rust-nsp-matchers)
starting a mental shift, and recent dives into [Clojure](http://clojure.org/) sealing the deal), I wanted to give it a shot. There were
a couple of strong draws for me:

* Defining a configuration for everything leads me to be able to port my configs from place to place
* The comfort to screw up; that is, the ability to roll back in case things got weird
* A new learning experience - it's been a long time since I've installed something new just to give it a shot.

I finally made the jump around mid-December, so I've been on it on my laptop for about a month and a half. Not constantly, but enough
that I could make the configuration my own, and start to find out where things have been good, and what still needs to improve (both
my understanding of Nixos and in the system).

## Packaging and Installation

Nixos provides an ISO from their website, as is often the case with linux distros. The only qualm that I had in this stage is that,
because I wanted to do a [UEFI installation via USB](https://nixos.org/wiki/Installing_NixOS_from_a_USB_stick), I had to jump through a 
few extra hoops in order to get things working correctly. This is less of a problem with Nixos specifically, and more endemic to the 
linux community as a whole - we need to make UEFI boot media easier to create. I miss the days of `dd if=<iso> of=<flash-drive>`. :)

I was pleasantly surprised to see that you could boot into a graphical environment using the boot media. This is something that some
[major distributions](https://www.archlinux.org/) still seem to miss, and I was impressed to see a relatively young distro include a UI, 
even if it didn't handle the installation that way. At the very least, it gives you a sense of "try before you buy."

The initial read-through of the [Nixos Manual](http://nixos.org/nixos/manual/index.html#sec-installation) were useful, but sparse in places where more
information (particularly for a new user) would have been helpful. As you can see, the first 9 steps are pretty straightforward - hard disk setup, loading
options for runtime, etc. 

*Then there's step 10.*

"Edit this file" has a lot of openness to it. They do give you some resources around here (links to detailed configuration docs, a 
very barebones initial config, and links to "real-world installations" to compare to), but as your first foray into the format
of the configuration.nix file, I would have **loved** a "guided tour" here, giving insight into why the file structure is how it is,
common configuration options for particular installation types, and a more "this is a normal base installation" config instead of the bare
config provided in the manual. 

In my mind, this is where *the magic* of Nixos happens - the documentation should highlight the magic,
calling out its importance and showcasing how the features work. Imagine my surprise (and delight!) when I found I could enable xorg with a single line:

```plain
services.xserver.displayManager.sddm.enable = true; 
```

Having fast, impacting implementations of large swaths of software fed through because a single option was enabled? Again, that's *magic*, and that's what needs advertised here.

Other first-order-of-business options for me at installation are things like:

* Adjusting the keyboard layout (Dvorak)
* Setting timezone and verifying current time (with optional NTP)
* laptop configuration (thinkpad module, touchpad config)

These were all fairly easy to find though the nixos wiki - when you've had other users run into the same configurations, 
it's always nice to have them easy to find.

The rest of the installation was a breeze - you run nixos-install, set your root password at the end, and then reboot into your new OS.

## Using it 

Now I had a base working system, and it only took me a few generations of building and rebooting. After about generation 6-7 or so, I had
things down to where I liked them for a start. From here, it was time to dig into what I could do with it.

### Getting comfortable...

My laptop is used for a couple of things:

* Internet browsing
* Programming projects
* Photo editing

Setting up to get these tasks done wasn't terribly hard. However, a *Really Cool Thing™* about Nixos is that you can do these *per user*.

Let me reiterate that:

> *Package installation can be done at the per-user level!*

That's huge in my mind, for multiple reasons. First, I'm sure I'm not the only one who will end up using this laptop. In the event that I need to *share*, this gives me a way to keep my configuration and structure completely isolated from another users, where I can have only the things I want installed for me. In addition, I don't have to muck about with installing everything system-wide, so I don't have to use the shared folders (and `sudo` calls everywhere) to do it. Finally, I like to configure tools in ways that could be confusing to others. This way, I can not only have the tool's configuration, but *the tool itself* in my user account.

In an effort to unite the ability to do this with the Nixos "declarative by nature" package management, `nix-env` was born:

```plain
$ nix-env -qaP nitrogen       # a package for setting wallpapers for non-standard WMs.
nixos.nitrogen        nitrogen-1.5.2
nixos-15.09.nitrogen  nitrogen-1.5.2

$ nix-env -i nitrogen         # to install
```

The end result is that the package has been added to the user environment, and a reference to the package has been added to the user manifest inside of their Nix profile. As an odd side-effect of how nix packages are stored, take a look at my system $PATH variable:

```plain
$ echo $PATH 
/home/ckruse/bin:/var/setuid-wrappers:/home/ckruse/.nix-profile/bin:/home/ckruse/.nix-profile/sbin:/home/ckruse/.nix-profile/lib/kde4/libexec:/nix/var/nix/profiles/default/bin:/nix/var/nix/profiles/default/sbin:/nix/var/nix/profiles/default/lib/kde4/libexec:/run/current-system/sw/bin:/run/current-system/sw/sbin:/run/current-system/sw/lib/kde4/libexec
```

Looking through that, you'll see the conspicuous absence of our friends in `/bin`, `/usr/bin`, and `/usr/local/bin`. These directories aren't used - instead, packages get built in their own compartmentalized sections, and then are symlinked into the running context (system-wide, at `/run/current-system/sw/bin`, and for each user, at `/home/<user>/.nix-profile/bin`). This is awesome for generating your environments while in the nix context, but it can have some troubles.

### ...and uncomfortable

About 2 weeks ago, I wanted to sit down and create this very blog post, detailing the experiences that I had with migrating things over. I mentioned this [in the past](/posts/yearly-refresh-time), but my blog posts are generated code based on Harp, a NodeJS static site generator.

In the past, I used to manage my node installations with the wonderful [nvm](https://github.com/creationix/nvm) version manager, a set of shell scripts which shim versions of node to be used. However, this spectacularly failed with my Nixos installation. Versions of node installed via nvm did not have access to runtime libs that they would need, and so proper symlinking and running of these node versions failed, and failed hard.

While stuck with this problem, I realized I could settle for using the system-provided Node version. However, this led to its own problems, because now we have two separate package managers (nix and npm). There is [a push](http://blog.lastlog.de/posts/nodejs_on_nixos_status/) to incorporate npm packages into nix using the npm2nix utility, but I feel that, especially given separate development projects have their own dependency manifests, this is a step in the wrong direction.

As a separate method to approach this problem, I read up on [creating separate script profiles]() for running your development projects in. I thought, "Great! I'll spin this up with the system node.js, install harp, and run with it." Unfortunately, the harp install fell through, because I didn't have write access to the global install directory for node (as I shouldn't, because then the implementation leaks out into the package build). 

For now, I'm using a separate dev host + ssh to update here. I'll want to dig into this further to see if I can get a good solution figured out. As of now, I know that `npm install -g <whatever>` isn't a good idea. Now I either find a better way (Docker could be a fun insert here), I change the blog backend again, or I wipe and start with a fresh installation of "new distro ++".

