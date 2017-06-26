---
title: "Rust, by a non-systems programmer (Part 1 of n)"
author: Christopher Kruse
author-email: ckruse@ballpointcarrot.net
author-url: http://www.ballpointcarrot.net
author-github: ballpointcarrot
author-twitter: ballpointcarrot
date-created: 2015-03-08
date-published: 2015-03-08
in-language: en
keywords: rust, rustlang, strings, io
canonical-url: http://www.ballpointcarrot.net/posts/rust-non-systems-programmers
tags:
 - rust
 - Programming
---
I've been working on trying to teach myself Rust for the past 4 months or so. It's been slow-going, because I'm having to
teach myself things that would have been common knowledge for anyone with previous experience doing systems-level programming
in C or C++. Instead, this will be more or less of a brain dump of a rubyist that is picking up some systems-level knowledge.

## String I/O

Coming from a scripting language like Ruby, basic string input and output is fairly straightforward:

```lang-ruby
puts "How old are you?"
age = gets.strip
puts "You've said #{age} years, which is #{(age.to_i*365.25).to_i} days."
```

In Rust, this becomes significantly more involved:

```lang-rust
#![feature(old_io)]
use std::old_io::stdin;
use std::str::FromStr;

fn main() {
    println!("How old are you?");
    let mut input = match stdin().read_line() {
        Ok(v) => v,
        Err(_) => panic!("Couldn't get STDIN!")
    };
    input = input.trim()
    let age_days:usize = match FromStr::from_str(input.trim()) {
        Ok(v) => (v * 365.25) as usize,
        Err(_) => panic!("Couldn't convert to usize!")
    };

    println!("You've said {} years, which is {} days", input.trim(), age_days)
}
```

In Rust, the placement of the variable in stack or heap memory is important to the Rust compiler's understanding of

* how long the variable lives (it's "lifetime"), and
* who owns the memory that the variable uses (the "ownership").

There are [plenty](https://doc.rust-lang.org/book/ownership.html) 
[of](http://rustbyexample.com/move.html) 
[posts](https://doc.rust-lang.org/book/strings.html)
[available](http://nercury.github.io/rust/guide/2015/01/19/ownership.html)
which will explain these terms to enough detail; for practical purposes, we have to manage two separate types of strings. 
Each type gets used in subtly different ways, but will cause you some problems when building out programs. 

The more commonly used string type is `&str`, also known as the "string slice". This string is allocated to the stack, and
is accessed through a reference to the stored string, much like a C-like character array.

The second string type, `String`, is used when you want to maintain ownership over the string value. `String`s are allocated on the heap, and
are "growable" - that is, they can be added to as mutable variables. 

Be careful to understand what type of string you'll be dealing with; functions generally operate on the more general `&str` type,
so you may need to convert back and forth between the two types with `String::as_slice` or `"".to_string()`.

### Handling input possibilities

Because Rust does not have a built-in concept of "null", you cannot assign a non-guaranteed value to a variable. Instead,
Rust provides the Result type, which wraps the successful value in an `Ok()`-wrapped response; otherwise, an error will be 
provided with the relevant error details. 

This means that reads from `STDIN` will be quite a bit more verbose than you're used to,
but the compiler now forces the checking of invalid situations for a value, which can take more off of your mind.

### Changing I/O Library

One of the last changes to the Rust Standard Library was an overhaul of the IO subsystem. Most documentation (including the 
official docs) request that you continue to use `std::old_io` until the overhaul is complete.

## Memory Management

One of the larges differences between the two languages is the presence/absence of a Garbage Collector. In Ruby (and other GC'd
languages), the GC is used to clean up expired references from the runtime, to periodically free unused memory from the 
application being run.

In Rust, there is no GC. Instead, the compiler has a notion of "ownership," as we discussed earlier. Check out the following 
snippet:

```lang-rust
    let x = Box::new(5);
    {   
        let y = x;
    }   

    println!("x == {}", x); 
```

In line 1, we create a new heap-allocated variable, and store 5 into it. Because it's on the heap, the value of x is really
a pointer to the memory location of the "Box" - the owned variable. Now, in line 3, we create the variable y, and store x's value 
into it; this is considered by the Rust compiler to be a "move" - both pointers point to the data, but rust treats y as the 
new owner of the Box, as x has transferred ownership. Because of this, line 6 fails to compile correctly, as the value owner 
has been moved:

```lang-plaintext
src/main.rs:7:25: 7:26 error: use of moved value: `x`
src/main.rs:7     println!("x == {}", x);
                                      ^
note: in expansion of format_args!
<std macros>:2:43: 2:76 note: expansion site
<std macros>:1:1: 2:78 note: in expansion of println!
src/main.rs:7:5: 7:28 note: expansion site
src/main.rs:4:13: 4:14 note: `x` moved here because it has type `Box<i32>`, which is moved by default
src/main.rs:4         let y = x;
                          ^
src/main.rs:4:14: 4:14 help: use `ref` to override
error: aborting due to previous error
```

Because of this, you need to keep track of what values you're using where, who's got the ownership of the value, and how long 
the value is alive. 

## Documentation!

This will be something that comes with more use and more exposure, but right now, outside of the [Rust standard documentation](http://doc.rust-lang.org/), there
isn't much for explanations on how/where to do things is, in comparison to the Ruby ecosystem on the Internet. As I get more
into it, I'm hoping to have more posts like this, where I can go into greater detail on how to do things in a rust-like way.

Moving into Systems programming is weird for me; I know there's some things that I'm missing, and I have a lot more power to 
play around with the system at a lower level, but I struggle with finding projects to work on. Either I choose something 
substantially big, or it ends up being examples on [/r/dailyprogrammer](http://reddit.com/r/dailyprogrammer). I'd like to put 
together something in the middle soon.
