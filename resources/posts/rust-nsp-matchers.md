---
title: "Rust (part 2 of n): 'match' and Tuples"
author: Christopher Kruse
author-email: ckruse@ballpointcarrot.net
author-url: http://www.ballpointcarrot.net
author-github: ballpointcarrot
author-twitter: ballpointcarrot
date-created: 2015-03-14
date-published: 2015-03-14
in-language: en
keywords: rust, rustlang, match, tuples
canonical-url: http://www.ballpointcarrot.net/posts/rust-nsp-matchers
tags:
 - rust
 - Programming
---
In order to keep practicing and make sure I keep sharp on what I'm learning, I like to dig through problem sets and write solutions for them. At first, the best resource for these was [Project Euler](https://projecteuler.net), but as time went on, new projects came about that presented problems in slightly different ways:

* [Codewars](http://codewars.com) gamifies the process a bit, by rating the difficulty of each item; those determine the amount of credit you get by solving them.
* [CodeKata](http://codekata.com), which is more of an exercise in repeated solutions to common problems, with the understanding that the repeated practice, you become better over time

Most recently, I've been running through problems on Reddit's [DailyProgrammer](http://reddit.com/r/dailyprogrammer) subreddit. These are community-submitted challenges, separated into easy, medium, and difficult problems. Each of these problems can extend within themselves to offer more flexibility or allow the user more options.

Today, I had the opportunity to work on the [most recent 'easy' DailyProgrammer challenge](http://www.reddit.com/r/dailyprogrammer/comments/2ygsxs/20150309_challenge_205_easy_friendly_date_ranges/). In it, when provided two separate dates, you need to make a pretty printout of the range between them. For example:
```lang-plaintext
2015-03-14, 2015-03-15 => March 14th - 15th
2015-03-14, 2016-02-28 => March 14th - February 28th
2015-03-14, 2016-03-15 => March 14th, 2015 - March 15th, 2016
```
Within the set values, you'll notice that years can be omitted when they match the current year, but only within a year's time. In addition, within a single month, you don't need to print out the month twice (the value is inferred).

### Rust: 'match'

During my solution to this problem, I had two problems to solve:

* How do I handle the ordinal values (1st, 2nd, 3rd, etc.) for the dates?
* How do I compare the dates from the original strings to determine the output?

For both, Rust has some strong utilities for helping with the solution. To solve the ordinal issue, rust provides a 'match' keyword, which acts as a more flexible form of most languages' switch/case statements. Within it, we can set matching values, or matching ranges of values, in order to retrieve what we're looking for.

In this case, we need 1st, 2nd, and 3rd to be unique, and 4 -> 20 to use 'th' (think about it... 11th, 12th, ...). However, we then reach 21, and that ends up using an 'st' again. Since our problem only concerns itself with days of a month, we can limit ourselves to an upper bound of 31, as the example below shows:

```lang-rust
fn ordinal(value: usize) -> Option<String> {
    match value {
        0 => Some(String::from_str("th")),
        1 => Some(String::from_str("st")),
        2 => Some(String::from_str("nd")),
        3 => Some(String::from_str("rd")),
        4...20 => Some(String::from_str("th")),
        21...31 => ordinal(value % 10),
        _ => None
    }
}
```

Some things to point out from the example:

* the fifth matcher uses a range from 4 to 20 inclusive, and will respond with a 'th'. This satisfies the requirement for the teens.
* the sixth matcher removes the tens place from the value, and recurses. when it responds, it will have the matcher for just that last place, which fulfilles the rule of 21, 22, etc.
* the last matcher is a *catch-all* or *don't-care* matcher; as we are unconcerned with the value, we just handle the case without the value. This will become more important in the next section.

Another point of note here is that the function is returning an Option value - as discussed [in the last post](/posts/rust-non-systems-programmer), an Option value is used in situations where you are uncertain about the resultant value, in order to avoid a situation where `null` would traditionally be used. Finally, the function does not have an explicit `return` keyword, because the match is acting as an expression and not a statement. By omitting the semicolon, the selected match logic is returned on its own.

### Rust: Tuples

Now that we have the ordinal solved, we can go about formatting the actual output. In order to do so, we need to compare the values of the dates to find the differences between them (less than a month, less than a year, more than a year, etc.). In order to solve this, I arranged the date properties into a *tuple* - a structure with multiple data points within it.

```lang-rust
let (start_yr, start_mo, start_dy) = start_values[0], start_values[1], start_values[2];
```

Tuples in Rust can be used in many ways; often, they're used to provide multiple values as a return of a function, or to store data that is paired or grouped together (like (x,y) coordinates).

My limited example above is referred to as a *destructuring* of a tuple - it allows creation of multiple variables from the tuple they were defined from. In other words, I can now use `start_yr` and `start_mo` in my code in other spots.

This example doesn't show much of the power of what is being done, but watch what happens when you pair it with the `match` statement from above:

```lang-rust
match (end_yr-start_yr, (end_mo as isize)-(start_mo as isize), (end_dy as isize)-(start_dy as isize)) {
    (0, 0, 0) => format!("{} {}",
                         MONTHS[start_mo-1],
                         print_ordinal(start_dy)),
    (0, 0, _) => format!("{} {} - {}",
                         MONTHS[start_mo-1],
                         print_ordinal(start_dy),
                         print_ordinal(end_dy)),
    (0, _, _) => format!("{} {} - {} {}",
                         MONTHS[start_mo-1],
                         print_ordinal(start_dy),
                         MONTHS[end_mo-1],
                         print_ordinal(end_dy)
```

Here, we generate a tuple that acts as the difference between the year, month, and day values. Given the assumption that our ranges move forward in time, we present three cases:

* The start and end days are the same. In this case, just print the month and day (you don't need to print the end day, as it's the same).
* The difference of the start and end month and year are both zero. In this case, we're within the same month, so we don't need to print it twice. Instead, we print the range between the start and end day.
* The year is the same, but we have two different months. In this case, print both months and days.

Notice that we don't care about what values we have in the month and day. In a situation where we *need* that calculation, we can assign variables to it, as well:

```lang-rust
    (1, month, day) => {
        let use_yr = match (0.cmp(&month) , 0.cmp(&day)) {
            (Ordering::Greater, _) => false,
            (Ordering::Equal, Ordering::Greater) => false,
            (_,_) => true
        };
        if use_yr {
            format!("{} {}, {} - {} {}, {}",
                    MONTHS[start_mo-1],
                    print_ordinal(start_dy),
                    start_yr,
                    MONTHS[end_mo-1],
                    print_ordinal(end_dy),
                    end_yr)
        } else {
            format!("{} {} - {} {}",
                    MONTHS[start_mo-1],
                    print_ordinal(start_dy),
                    MONTHS[end_mo-1],
                    print_ordinal(end_dy))
        }
    }
```

Here, we need to know the difference between month and day, as the year changed. However, if the month difference is less than zero (eg: from 2015-12-01 to 2016-03-01), then the year is assumed to be changing, and not printed. By extension, we will do the same with the date (eg: 2015-12-31 -> 2016-12-25).

We capture those differences from the matcher in the month and day variables, and then use those to make further determinations in the matcher's block. First, we figure out if we are using the year, based on the conditions above. Then, we choose a format for the date based on if we're using the year.

### Testing

Rust includes a strong preprocessor, which will examine and selectively compile parts of your code. Based on this, you can keep your unit tests contained within your source file, and they will only be compiled when you run in a test configuration (usually using 'cargo test').

You can find the crate for my solution [on my github repository](https://github.com/ballpointcarrot/rust-dailyprogrammer/tree/205). Note that there are different branches - I'll hopefully add more solutions to new branches for each problem that's solved.
