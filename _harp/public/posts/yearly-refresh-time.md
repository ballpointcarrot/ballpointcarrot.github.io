I don't know if it's the digital form of spring cleaning, or if there's a standing need to clean things up
and keep them looking nicely, but I found it time this weekend to update the visual and functional aspects
of my blog. With that being said, welcome to ballpointcarrot.net, version...4, I think?  I haven't done a 
terribly good job of keeping an active archive, and haven't consulted the Internet Archive to see if they
have any of the previous iterations.

With the new layout comes a new backend engine for producing the contents - I've moved from Octopress 
(which, while nice, dictated a structure and tooling a little more than necessary) to a Node.js static 
site generator called [Harp](http://harpjs.com/). 

Because this is a site generator first, the structure used for blog-like things, such as tags, feeds, etc.
come as extras; It took a little bit of searching to find a theme and pattern I liked, and still didn't 
fit 100%. Fortunately, because of the flexibility provided, I adjusted the structure of how data was read
and inserted, and made it truly my own.

## About the new platform ##

As I mentioned, the base of the new platform is Harp. The [base theme](https://github.com/kennethormandy/hb-casper) 
is a port of [Casper](https://github.com/TryGhost/Casper), which is the default theme for the 
[Ghost](https://ghost.org/) blogging platform. In addition, I've added a more flexible syntax 
highlighting library called [Prism](http://prismjs.com/) to help with code snippets (which I'll be able 
to use more frequently, I'm hoping!). 

```javascript
christopher.blog_upgrade = true;
```

Overall, the site is still set to run on GitHub Pages; I appreciate
that they allow hosting like that, and moreover, allow the Hostname redirection that they do.

Migration of the actual data from the old platform to the new one worked pretty well. As they say, the 
best knowledge transfer medium is plain text. No reading from databases, no conversions; just a little bit
of modification of the markdown files in one platform to the other.

## Now the question - _will he keep updating it?_ ##

That will remain to be seen. I had a goal of writing every two days; we saw how well that went. But, I've
been working on teaching myself new things in the intervening time, and I think it may prove to be good 
not just for me, but for people reading, to start putting my thoughts to paper, so to speak.

I'm cautiously optimistic that I can do a better job of updating things. We'll see if I can make a pattern
out of it.
