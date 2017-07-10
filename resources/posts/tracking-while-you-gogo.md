---
title: Tracking while you Gogo
uuid: 77c34553-ad48-4109-9c71-5c3b004e816d
author: Christopher Kruse
author-email: ckruse@ballpointcarrot.net
author-url: http://www.ballpointcarrot.net
author-github: ballpointcarrot
author-twitter: ballpointcarrot
date-created: 2017-01-09
date-published: 2017-01-09
in-language: en
keywords: airplanes, gogo, inflight, wifi, programming, data
canonical-url: http://www.ballpointcarrot.net/posts/tracking-while-you-gogo
tags:
 - airplanes
 - Programming
 - fun
---
I'm flying a lot more as part of my current job. As I write this, I'm on a flight from Seattle to Salt Lake City (first of a connection). I enjoy the fact that I can connect to the internet while going over 500 MPH at nearly 30,000 feet in the air - it's a wonder of science that I can do these things.

However, sometimes the cost of getting the in-flight internet is a little over-the-top. Most of the time, I'll connect to the hotspot, but then use my laptop offline (http://devdocs.io offline mode is perfect for this).

---

On one of my last flights, though, I wanted to see just what I had access to. On Delta flights, there's a flight status tracker on the web portal during the flight, which provides details about how the flight is going, times to arrival, and other flight data. A data nerd by nature, this is something I want, and normally pull up on the seat-back entertainment system (instead of watching a movie like a normal human being.)

So, I chose to dive in to the Gogo portal to see what was possible. A quick look at the dev console in Chrome shows how the flight data page loads:

![Chrome tools](/assets/img/delta-gogo-chrome-devtools.png)

To my delight, not only do we have JSON requests for data, but they're straightforward, easy to parse GET requests (easier to deal with than POSTs). To see how these are requested, I ran a quick `dig` call against each of the FQDNs that were presented.
```plain
Dig responses:
airborne.gogoinflight.com: 10.241.xxx.xxx
utils.gogoinflight.com: 10.241.xxx.xxx
d.sda.gogoinflight.com: -> gogoair-d3.openxenterprise.com -> 173.241.xxx.xxx
fig.gogoinflight.com:
        10.246.118.xxx
        10.246.119.xxx
        10.246.120.xxx
```

Everything is in private class addressing space, meaning all of this routes internally within Gogo's network. Meaning, we should be able to make these requests both inside and outside of the browser.

The first we'll look at, "statusTray", doesn't even need any parameters:
`curl http://airborne.gogoinflight.com/abp/ws/absServices/statusTray` gives me a straight JSON output of random details, including (amusingly) "gogoFacts", a random fact that Gogo provides as interesting info (and a little bit of advertisement).
```javascript
{
   "Response" : {
      "serviceInfo" : {
         "service" : "Inactive",
         "remaining" : 0
      },
      "status" : 200,
      "gogoFacts" : "The world record for the longest Wi-Fi connection is" +
      "237 miles, held by Ermanno Pietrosemoli of Venezuela, who transferred"+ 
      "about 3 MB of data between the mountaintops of El Aguila and Platillon.",
      "flightInfo" : {
         "tailNumber" : "N3753",
         "HSpeed" : 576.4907,
         "longitude" : -119,
         "utcTime" : "2017-01-09T18:36:18.583Z",
         "expectedArrival" : "2017-01-09T19:34:14Z",
         "airlineCode" : "DAL",
         "acpuVersion" : "10.4.0",
         "altitude" : 33882.277,
         "flightNumberInfo" : "DAL1319",
         "latitude" : 45.37,
         "videoService" : false,
         "abpVersion" : "7.0.1",
         "departureAirportCode" : "KSEA",
         "destinationAirportCode" : "KSLC",
         "VSpeed" : 0.6136283
      }
   }
}
```

> Interestingly enough, this response was different on my flight today vs. the flight last week I played around with these requests. Your response(s) may be different. For instance, my new flight only responded with ICAO airport codes, and no IATA (3-letter) codes. The second request can use either, from what I can tell.

Based on this output, we can use a number of these values to craft the request for the "flightStatusByLeg" request:

```ruby
"http://utils.gogoinflight.com/flightService/flightStatusByLeg?"+
"flightNum=#{data["Response"]["flightInfo"]["flightNumberInfo"]}"+
"&depDate=#{Time.now.strftime("%Y-%m-%d")}"+
"&originCode=#{data["Response"]["flightInfo"]["departureAirportCode"]}"+
"&destCode=#{data["Response"]["flightInfo"]["destinationAirportCode"]}"
```

This request comes back as JSONP, or a JSON block wrapped in an executable function (a common method of executing code on callback in an AJAX request). If you're parsing it as JSON, you'll have to remove the function call before parsing:

```ruby
data2json = JSON.parse(data2.gsub!("DLFSCallback (","").gsub!(/\);$/, ""))
```

This data set is much larger; mostly, because it gives you a sequenced point-to-point data stream of latitude, longitude, ground speed, and altitude! This data could be plotted and graphed in any way you see fit (my goal for next flight is to tie it to D3.js to plot my flight status :D).

```javascript
{
   "flightStatusResponse" : {
      "flightStatusByLegResponse" : {
         "flightStatusTO" : {
            "flightOriginDate" : "2017-01-09T00:00:00.001-05:00",
            "flightNumber" : "1319",
            "changeOfAircraft" : "false",
            "flightPositionTO" : {
               "altitude" : "37000",
               "inFlightTimeMinutes" : "55",
               "actualPositions" : [
                  {
                     "timeOver" : "0",
                     "sequence" : "1",
                     "latitude" : "47.4488",
                     "groundSpeed" : "0",
                     "longitude" : "-122.30944",
                     "altitude" : "433"
                  },
                  {
                     "groundSpeed" : "0",
                     "latitude" : "47.4462",
                     "sequence" : "2",
                     "timeOver" : "1483985273",
                     "longitude" : "-122.3081",
                     "altitude" : "300"
                  },
                  {
                     "longitude" : "-122.3082",
                     "altitude" : "1700",
                     "latitude" : "47.424",
                     "groundSpeed" : "0",
                     "timeOver" : "1483985303",
                     "sequence" : "3"
                  },
                  {
                     "altitude" : "1800",
                     "longitude" : "-122.3072",
                     "groundSpeed" : "187",
                     "latitude" : "47.42333",
                     "timeOver" : "1483985305",
                     "sequence" : "4"
                  },
                  //...
```
Anyway, this is all retrievable without paying for the Gogo wifi; just a way to keep you entertained during the flight.
