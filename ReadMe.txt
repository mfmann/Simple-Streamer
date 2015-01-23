Simple Streamer is a simple streaming application that can stream webcam images.

https://github.com/sarxos/webcam-capture library is used for capturing images from the webcam.

**No security features have been applied to this application 

Protocol:When the client connects to the server, both the client and server will initiate a stream via a TCP connection. A stream is a sequence of JSON messages written to the socket. The first message of a stream is a StartStream message to indicate that the stream is starting. Subsequent messages are Image messages, each message containing image data. When the stream is to terminate, the final message is a StopStream message. After this message has been received, the socket can be closed. The client and server are sending this stream simultaneously.

To use Simple Streamer:

Run SimpleStreamer.jar with the following command line execution: 

java -jar SimpleStreamer.jar [-sport X] [-remote hostname1,hostname2,...    [-rport Y1,Y2,...]] [-width W] [-height H] [-rate Z]


sport = server port to use (default is 6262 if no server port is given)

remote = specifies a comma separated list on hostname to connect to 

rport = specifies a comma separated list of port numbers for the remote servers

width = desired image height

height = desired image width

rate = sleep time that is desired in miliseconds 
