---
layout: post
title: "Learning a new Editor"
date: 2014-02-07 22:03
comments: true
categories: Programming
---

## Background

I often fall back to my (now well-worn) copy of [The Pragmatic Programmer](http://pragprog.com/book/tpp/the-pragmatic-programmer) to take a look at things I can do to hone my abilities as a programmer. Recently, the two rules that have most recently hit me as things I haven't put much focus on are *Invest Regularly in Your Knowledge Portfolio*, and *There Are No Final Decisions*. In a fit of Christmas downime enegry, I decided to incorporate these principles by taking myself out of my normal comfort zone, and cutting out one of my most widely used tools - vim.

I've been a vimmer for many years, and I've incorporated utilities passed down from greater minds than I (Tim Pope, the guys at Pivotal, etc.) and had become comfortable in how I used my text editor day-to-day. I had a nice workflow set up, utilizing multiple vim tabs and separate sessions within tmux windows, and life was pretty good.

Then, in December, the challenge was set: strip vim out of my workflow for good, and find something else.

Naturally, and guided by one of my coworkers, I managed to settle on what is generally considered to be the prime competitor to vim - Emacs.

## Baby Steps

I've had forays into learning Emacs before, but I've always shied away, after playing with some basic tooling, and forgetting how to close it more often than I care to admit. This time was different, though; I had more years of experience under my belt, and it was more about getting myself into an uncomfortable place before I gain more knowledge.

My first thought was to find some type of starting guide. While the Emacs tutorial helped in terms of understanding navigation and simple editing, it didn't really show me the true power of the environment that I had gained. A cursory google search brought me to the [Emacs Starter Kit](https://github.com/technomancy/emacs-starter-kit). This toolkit provides some default setting to get you started with the environment; I used the documentation around it to gain knowledge of things like Melpa and Marmalade, which act as Emacs Package Managers of sorts.

Following my initial setup with the starter kit, I started delving into the [emacs wiki](http://www.emacswiki.org/emacs/). This became a much more handy resource to me, as it provided me with multiple provided solutions to given issues that I've encountered while converting my vim-based mind to a constantly-insert-enabled editor. When I'd run into an issue where I found something I'd like to do similar to vim, I'd head to the wiki to find what analogs are available. Once I'd find a package which did what I looked for, I'd test it out with an M-x pacakge-install, and incorporate it into my init.el when I felt I had it down.

#### Evil?

I had been asked while converting why I decided not to use a vi-like editor package for Emacs (like Evil or Viper). I felt that, if I were to do this, it would be skimping out on what I should be learning, and I'd get less of an understanding of Emacs because I was sticking to my old method of doing things. Thus, I opted for Emacs's standard of editing, and no vi-emulation modes.

## Post-move

These are the things that I've discovered in the two months since I switched:

* Split pane support. I actually like the way that Emacs splits its windows better than vim. A C-x 2 or C-x 3 is an easy way to remember it; coupled with the 'workgroups2' package, configurations can be stored across sessions, too.
* Lisp. This has been a slow one for me, but I'm becoming more interested in learning Lisp and Lisp-like languages, like Clojure. I have a lot of work to do here, and that will be something to work on in the future.
* Tramp. Being able to edit files across an SSH tunnel is a fantastic feature.
* Buffers without files. Since the editor is meant to live on indefinitely, it's nice to spin up a new buffer and set the mode(s) I want, and let it reside in memory for a while before saving it. If I need to, a save is close enough away, but it works wonders for when I need to take some quick notes.
* ERC. I no longer need a separate IRC cilent; Emacs handles it for me. This is fantastic for me at work, where we frequently use chat to talk around.

Things I wish I had from Vim:

* Sentence-like movement. I've sort of rebuilt this on my own with a combination of line-jumps (M-x goto-line) and ace-jump-mode, but being able to translate "^3wci(" into what it can do in Vim is a skill I have yet to master.
* Mixing modes in HTML. This was noticeably less of a problem in Vim, but I don't know if it was due to the editor or my lack of noticing that showed just how difficult it is to switch contexts from HTML/CSS/JS all in the same file. I've tried doing the nXML hack and multiple modes, but those have worked to no avail.
* Closing the editor. True, I can leave Emacs. But, I miss popping up a new window for one file, and then leaving right after a quick edit.
* Workspace management. Because of the way I separated files into tabs in Vim, I knew each tab matched a different section of what I was working on (or another project entirely).  I lack this with the way that buffers are managed by default in Emacs. I'm still looking for better ways of handling this management, though - workgroups2 has been a nice start.

Things I know I still have yet to learn:

* More Lisp. I've gotten a good start, but that's a deep rabbit hole.
* Shell, email within Emacs. I used to use a lot of separate tools - now that Emacs contains most of them, I'd like to get better at handling those tasks within Emacs.
* Org-mode. I've seen my coworker pull off some crazy things with scheduling and notes within Org-mode, and I'd love to get to the point where I can do them, too.
* Macros and scripting. My next Pragmatic tenet is *Write Code That Writes Code*; I think getting your editor set to facilitate your programming counts. :)

We'll see what the next few months hold.
