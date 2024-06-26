# WebPockyToASCII
PockyToASCII on the web powered by Spring

### USAGE
Run project in IntelliJ.
cURL POST to /images with a small image of your choice as well as the format
cURL GET to /images to get all ids
cURL POST to /asciiart with the id of above image and the format of ASCII ART you desire : "HTML", "ANSI" or "raw" ASCII
cURL GET to /asciiart/{id} and voila
