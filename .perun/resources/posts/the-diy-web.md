---
title: The DIY Web
uuid: 2d25d633-a37a-4853-ab37-2de82245fd33
author: Christopher Kruse
author-email: ckruse@ballpointcarrot.net
author-url: http://www.ballpointcarrot.net
author-github: ballpointcarrot
author-twitter: ballpointcarrot
date-created: 2017-08-21
date-published: 2017-08-25
in-language: en
keywords: facebook, federation, mastodon, matrix, distributed, decentralized
canonical-url: http://www.ballpointcarrot.net/posts/the-diy-web
tags:
 - Internet
 - Social Media
 - Software
---
> TL;DR: Building on basic email, and with the advent of Mastodon, XMPP, Matrix and more,
using and building federated networks could bring immesurable improvements to the Internet.

### On Walled Gardens

One of the more controversial decisions I've made for myself in the past few years
was to take myself off of the Facebook platform. While this makes it far harder for
friends and family to scout me out on the Internet as a whole, I had reservations
about using their platform, and ultimately deactivated my account in early 2011. The reason for this mostly stems out of two main points:

 - The first takes us way back to April of 2010. Back then, Facebook
 [introduced a change](http://lifehacker.com/5549394/how-to-return-facebook-privacy-settings-to-what-you-signed-up-for)
to their platform; as a result, all users' permissions defaulted to be [public by default](https://www.wired.com/2010/05/facebook-rogue/). This
caused a fair amount of backlash at the time, and ultimately caused the FTC [to sanction
Facebook](https://www.ftc.gov/news-events/press-releases/2011/11/facebook-settles-ftc-charges-it-deceived-consumers-failing-keep) as a result of this and earlier privacy concerns.
 - The second is due to their need to create a wide break between content on the inside of
 their platform and the rest of the Internet (the "walled garden", as is often used). As a
 result of this separation, users who either are not on Facebook at all, or may be logged
 out for the time being, lose the ability to access media and content stored there.

The Internet was founded with the principle of enabling people to share ideas - the existence
of some of the largest Internet companies today started out by enabling people to find and
share information. By limiting access to resources, data and content to only a select subset
of individuals, you restrict the ability for people to grow from those ideas. I'm not saying that
there can't be some access limits (although, if you want to keep something private, maybe putting
it on a global network isn't the best idea - better argument for a different day, though);
content creators want to retain use and control of their content. However, at the scale in which
Facebook exists, the limitations of access to those resources is stifling to those who could
use it.

### On Centralization

Facebook, as well as Twitter, Instagram, Google, etc. all share something in common: they have
one point of access. When that access is cut off or limited (think of Github outages, which have
been dubbed "developer holidays" based on their impact to the tech community), it can be disruptive
to a large amount of people. In addition, by selecting one route to access for these services,
the companies that run them can start to dictate consumption:

 - Twitter heavily limits access for third-party phone apps into the platform
 - Snapchat doesn't provide a Windows Phone app, but [requested Microsoft pull third-party apps](http://www.pcworld.com/article/2862088/snapchat-cracks-down-on-windows-phone-imitator-apps.html) providing
   that functionality from their app store
 - the implementation, backlash and subsequent removal of YouTube's ["Real Name" policy](https://gigaom.com/2013/11/11/users-outraged-over-youtubes-switch-to-google-real-names-policy/).

This centralization shows that, as we allow sole consumption from these groups, we risk reducing access
to them; as a way of providing robustness to the Internet, there should be more than these single
players in their respective spaces, so that we can operate in the event something goes down.

### Federation

This spring, Twitter made the (unfortunate) decision to update some of their UI - most notably,
they [removed usernames from replies in messages](https://motherboard.vice.com/en_us/article/d7qkmx/the-new-twitter-replies-are-giving-me-an-ulcer).
People didn't enjoy this (as noted above). Whenever there's a big event like this (Reddit has had them
a number of time in their past, too), a subset of users start to look for competing products. Around this time,
people discovered the Mastodon project, which provides a Twitter-like interaction style, but with a
couple of major shake-ups:

 - You had 500 characters (If you've tried it after years on Twitter - it's SO MUCH SPACE).
 - There's a "Content Warning" function, which hides things that may be offensive or may cause discomfort.

The major difference (for the sake of our discussion) was that, instead of being one service,
it is a network of separate running copies of the server software, run independently (called mastodon
"instances"). This brings in some tradeoffs: for example, since you can organize communities on separate instances,
those communities can start to build on specific topics. However, this doesn't mean you're limited to just the
people on your instance. Through a process called "Federation", users on one instance can follow users from another;
the server then keeps that user and their instance up to date for the users that follow them on their home instance.

> NB: The author of the above article tried out Mastodon around this time too - [you should see her take on it](https://motherboard.vice.com/en_us/article/783akg/mastodon-is-like-twitter-without-nazis-so-why-are-we-not-using-it).

A big driver for me after being introduced to Mastodon is the power to do things your way. I run my own personal instance
(at https://social.ballpointcarrot.net); this gives me the ability to personalize and customize my instance to how I
need it to operate. However, due to the communication protocol (GNU Social) underlying it, all instances have the capability
to communicate with each other.

## The DIY Web

I would love to take this concept of widespread, federated or decentralized services, which define a common protocol for
communication, and see them grow on the Internet. Moreover, since these give you the ability to host them for personal or
individual use, I would love to see the rise of the "DIY Web" - media, content and resources shared the way you want them
to be. I see great benefits to having these platforms: Technology literacy, Robustness of content, Better software as a
result of the needs around these platforms, all while retaining the social aspect of interacting and showing our voices.

Email is exactly this. Not everyone has (or even wants, in cases) a Gmail account. There is no absolute requirement that
your email go to Yahoo/Hotmail/Gmail/etc. - Running an email server is something you can do for your self, your household,
your extended family. The requirements are straightforward:

 - Set up the software to receive the email.
 - Set up the accounts to have the mail delivered to.
 - Tell everyone how to get their email (configure the clients).

The problem here is, those steps are **hard**. Most people aren't taught how installing packages on their desktop goes -
getting them to install and configure a mail server would be insane.

With the current technology teaching path, that is.

Think about a curriculum that uses setting up a project like this as its goal. In it, this gives you the ability to talk
about many concepts about computing that would largely go untouched. Networking (how do things go in and out to the Internet),
configuration of software (not just installing it), understanding how the messages are created and handed off from
place to place - there's a **lot** of detailed information that can be learned, just by trying to solve this as an exercise.
Once these people who have gone though this new training, they're going to be better set for dealing with technology out
in their daily lives (and hopefully with less of the "oh I'm not good with computers" hand-waving affectation).

At the same time, this is a place where those in the technology space can help. Part of the difficulty in this process
is that the resources that are being dealt with are built in a way that "technology people" know how to control. If
we can take some time and energy to make explaining the concepts here easier, and to simplify the process of building
out these types of applications, then we can make it easier to extend these types of federated networks out to the
world writ large. This is a perfect place for something like Docker - a premade container of a suite of coordinated
services will help to allow easy installation and execution of systems that would otherwise take hours to properly
set up and activate - hours that some at the target end don't have to spend in the review of those setup instructions.
Once these improvements are in place, we can then turn to making the communications protocols that these systems will
use more stable, more robust, and provide new features and extensions to them.

I'm excited to see where federated applications like this can take us. The more these can be used, the better off
we are in terms of open platform access, robustness (as the data propagates out to other nodes), and the benefits
brought in from the learning landscape, as well as benefits to the software itself.
