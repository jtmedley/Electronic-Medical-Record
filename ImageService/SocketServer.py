#!/usr/bin/env python3
import traceback #stack traceback, interpreter mimicking, error checking
import socket #socket connection library
import struct #c-like data structures, interpret packed binary data
import json #json file manipulation, encoder and decoder
import time #time tracking for speed etc.
import errno #symbolic error codes
import ssl #secure socket layer library for network connections
import os #operating system interface library
from PIL import Image #image manipulation
from socketserver import TCPServer, ThreadingMixIn, StreamRequestHandler #serve program over secure socket with TCP, multithreading, file streaming
import face_recognition #open-source facial recognition library
import numpy as np #matrix manipulation
import pyodbc #SQL database connection
# Adapted from https://stackoverflow.com/questions/23828264/how-to-make-a-simple-multithreaded-socket-server-in-python-that-remembers-client
# and from https://stackoverflow.com/questions/11647046/non-blocking-socket-error-is-always
# and from https://gist.github.com/khssnv/be4fa33e40d40d4da609bcf46ad5658d
# and from https://pillow.readthedocs.io/en/3.1.x/reference/Image.html
# and from https://github.com/ageitgey/face_recognition/blob/master/examples/face_distance.py
# and from https://stackoverflow.com/questions/33725862/connecting-to-microsoft-sql-server-using-python

#Facial recognition algorithm delivered via socket server
#
class face_comparison:
    #Incoming image to be compared to images in database
    search_pt_pic = ''
    #Array of all patient ID numbers associated with image features queried from database - no patient information, no image files
    pt_id = []
    #Error code or float array of facial features sent to client device to be associated with potential new patient record in database
    encoding_to_return = float()
    #List of patient ID numbers associated with matched patient images - no patient information
    matching_patient_ids = []
    #Facial features sent to client device to be associated with potential new patient record in database
    search_face_encoding = []

    #Constructor - receive input image and detect faces
    #search_pt_pic_input - image to be analyzed
    #optOut - flag indicating whether patient opts out of facial recognition - still need to detect faces
    #Case note: Patients can opt out of facial recognition but opt in to having their picture taken for their record
    #All images therefore must at least be checked for face detection
    def __init__(self, search_pt_pic_input,optOut): 
        try:
            #Set received image 
            self.search_pt_pic = search_pt_pic_input;
            #Applying facial recognition library to detect faces and extract feature encodings in images
            search_image = face_recognition.load_image_file(self.search_pt_pic)
            #Count how many faces were detected
            encoding_success = len(face_recognition.face_encodings(search_image))
            print("encoding success:")
            print(encoding_success)
            #Set member variables based on number of faces detected
            #Case: Face detection finds no faces
            if encoding_success == 0:
                print("NoFaceError")
                print("Error: No face is detected in this photograph.")
                self.faces_in_img_err = 0
                self.matching_patient_ids = [0]
                self.encoding_to_return = 0
            #Case: Face detection finds multiple faces
            elif encoding_success > 1:
                print("MultipleFaceError")
                print("Error: Two or more faces are detected in this photograph.")
                self.faces_in_img_err = 2
                self.matching_patient_ids = [0]
                self.encoding_to_return = 0
            #Case: Face detection finds 1 face - desired
            #Call function to match encoding against all encodings from database
            else:
                self.faces_in_img_err = 1
                self.search_face_encoding = face_recognition.face_encodings(search_image)[0]
                print("Current encoding:")
                print(self.search_face_encoding)
                print("Encoding as array")
                print(np.asarray(self.search_face_encoding))
                print("Finding matches")
                self.__find_matches__(optOut)
        #Catch error in parsing sent images/constructing without valid image
        #Set member variables to indicate error and exit
        except AttributeError:
            traceback.print_exc()
            print("NoImageError2")
            print("Error: No image found")
            self.faces_in_img_err = 3
            self.matching_patient_ids = [0]
            self.encoding_to_return = 0;    
            
    #Connect to database, query for all feature encodings, compare new encoding to all existing encodings, identify matching patient IDs
    def __find_matches__(self,optOut):
        #Apply confirmed single face encoding to be sent to client
        self.encoding_to_return = self.search_face_encoding
        #Exit if patient opts out of facial recognition, otherwise continue
        if(optOut != '1'):
            #Connect to database and query all 128 face ancoding values from all patient images
            #Note: Globalize and protect password for additional security
            self.matching_patient_ids = [0]
            passwordstring = "BME4925CAP201STONE82019TABHYUJAJONCKNJIITHA4"
            connectstring = "DRIVER={SQL Server};Server=localhost;Database=Testdb;UID=sa;PWD=" + passwordstring + ";Trusted_Connection=yes;"
            cnxn = pyodbc.connect(connectstring)
            query = ("""SELECT [Person ID] as Person_ID
                  ,[Num 1] as Num_1
                  ,[Num 2] as Num_2
                  ,[Num 3] as Num_3
                  ,[Num 4] as Num_4
                  ,[Num 5] as Num_5
                  ,[Num 6] as Num_6
                  ,[Num 7] as Num_7
                  ,[Num 8] as Num_8
                  ,[Num 9] as Num_9
                  ,[Num 10] as Num_10
                  ,[Num 11] as Num_11
                  ,[Num 12] as Num_12
                  ,[Num 13] as Num_13
                  ,[Num 14] as Num_14
                  ,[Num 15] as Num_15
                  ,[Num 16] as Num_16
                  ,[Num 17] as Num_17
                  ,[Num 18] as Num_18
                  ,[Num 19] as Num_19
                  ,[Num 20] as Num_20
                  ,[Num 21] as Num_21
                  ,[Num 22] as Num_22
                  ,[Num 23] as Num_23
                  ,[Num 24] as Num_24
                  ,[Num 25] as Num_25
                  ,[Num 26] as Num_26
                  ,[Num 27] as Num_27
                  ,[Num 28] as Num_28
                  ,[Num 29] as Num_29
                  ,[Num 30] as Num_30
                  ,[Num 31] as Num_31
                  ,[Num 32] as Num_32
                  ,[Num 33] as Num_33
                  ,[Num 34] as Num_34
                  ,[Num 35] as Num_35
                  ,[Num 36] as Num_36
                  ,[Num 37] as Num_37
                  ,[Num 38] as Num_38
                  ,[Num 39] as Num_39
                  ,[Num 40] as Num_40
                  ,[Num 41] as Num_41
                  ,[Num 42] as Num_42
                  ,[Num 43] as Num_43
                  ,[Num 44] as Num_44
                  ,[Num 45] as Num_45
                  ,[Num 46] as Num_46
                  ,[Num 47] as Num_47
                  ,[Num 48] as Num_48
                  ,[Num 49] as Num_49
                  ,[Num 50] as Num_50
                  ,[Num 51] as Num_51
                  ,[Num 52] as Num_52
                  ,[Num 53] as Num_53
                  ,[Num 54] as Num_54
                  ,[Num 55] as Num_55
                  ,[Num 56] as Num_56
                  ,[Num 57] as Num_57
                  ,[Num 58] as Num_58
                  ,[Num 59] as Num_59
                  ,[Num 60] as Num_60
                  ,[Num 61] as Num_61
                  ,[Num 62] as Num_62
                  ,[Num 63] as Num_63
                  ,[Num 64] as Num_64
                  ,[Num 65] as Num_65
                  ,[Num 66] as Num_66
                  ,[Num 67] as Num_67
                  ,[Num 68] as Num_68
                  ,[Num 69] as Num_69
                  ,[Num 70] as Num_70
                  ,[Num 71] as Num_71
                  ,[Num 72] as Num_72
                  ,[Num 73] as Num_73
                  ,[Num 74] as Num_74
                  ,[Num 75] as Num_75
                  ,[Num 76] as Num_76
                  ,[Num 77] as Num_77
                  ,[Num 78] as Num_78
                  ,[Num 79] as Num_79
                  ,[Num 80] as Num_80
                  ,[Num 81] as Num_81
                  ,[Num 82] as Num_82
                  ,[Num 83] as Num_83
                  ,[Num 84] as Num_84
                  ,[Num 85] as Num_85
                  ,[Num 86] as Num_86
                  ,[Num 87] as Num_87
                  ,[Num 88] as Num_88
                  ,[Num 89] as Num_89
                  ,[Num 90] as Num_90
                  ,[Num 91] as Num_91
                  ,[Num 92] as Num_92
                  ,[Num 93] as Num_93
                  ,[Num 94] as Num_94
                  ,[Num 95] as Num_95
                  ,[Num 96] as Num_96
                  ,[Num 97] as Num_97
                  ,[Num 98] as Num_98
                  ,[Num 99] as Num_99
                  ,[Num 100] as Num_100
                  ,[Num 101] as Num_101
                  ,[Num 102] as Num_102
                  ,[Num 103] as Num_103
                  ,[Num 104] as Num_104
                  ,[Num 105] as Num_105
                  ,[Num 106] as Num_106
                  ,[Num 107] as Num_107
                  ,[Num 108] as Num_108
                  ,[Num 109] as Num_109
                  ,[Num 110] as Num_110
                  ,[Num 111] as Num_111
                  ,[Num 112] as Num_112
                  ,[Num 113] as Num_113
                  ,[Num 114] as Num_114
                  ,[Num 115] as Num_115
                  ,[Num 116] as Num_116
                  ,[Num 117] as Num_117
                  ,[Num 118] as Num_118
                  ,[Num 119] as Num_119
                  ,[Num 120] as Num_120
                  ,[Num 121] as Num_121
                  ,[Num 122] as Num_122
                  ,[Num 123] as Num_123
                  ,[Num 124] as Num_124
                  ,[Num 125] as Num_125
                  ,[Num 126] as Num_126
                  ,[Num 127] as Num_127
                  ,[Num 128] as Num_128
              FROM [Testdb].[dbo].[Images]""")
    
            #Execute query
            cursor = cnxn.cursor()
            cursor.execute(query)
            face128_float = 0
            num_pt_int = 0;
            all_face_encodings = []
            #Concatenate face encodings for single comparison command
            for row in cursor:                
                try:
                    face128 = np.zeros(128)
                    for x in range (128):
                        attribute_string = 'Num_' + str(x + 1)
                        face128_float = float(getattr(row, attribute_string))
                        face128[x] = (face128_float)
                    all_face_encodings.append(face128)
                    single_pt_id = getattr(row, 'Person_ID')
                    self.pt_id.append(single_pt_id)
                    print(single_pt_id)
                    num_pt_int = len(all_face_encodings)
                except TypeError:
                    traceback.print_exc()
                    print("Corrupted data in database")
                except ValueError:
                    traceback.print_exc()
                    print("Corrupted data in database")
            #Apply face recognition library to record distances (differences) between encodings if database is not empty (no encodings)
            #Smaller distance indicates closer match
            if num_pt_int > 0:
                results = face_recognition.face_distance(all_face_encodings, self.search_face_encoding)
                matched_pt = []
                print("Results")
                #Process face distances for all encodings, extract associated patient IDs for matches only
                for i, face_distance in enumerate(results, 0):
                    print("The test image has a distance of {:.2} from known image #{}".format(face_distance, i))
                    print("- With a normal cutoff of 0.6, would the test image match the known image? {}".format(face_distance < 0.6))
                    print("- With a very strict cutoff of 0.5, would the test image match the known image? {}".format(face_distance < 0.5))
                    #Calculated face distance threshold for matches from independent statistical analysis: 0.47
                    if face_distance < 0.47:
                        print("The matched patient id is ", self.pt_id[i])
                        matched_pt.append(self.pt_id[i])
                        print("It's a match")
                    else:
                        print("It's not a match")           
                if not matched_pt:
                    print("NoMatches")
                    self.matching_patient_ids = [0]
                    print(self.matching_patient_ids)
                    print(self.encoding_to_return)
                else:
                    matched_pt.sort(key=None, reverse=True)
                    self.matching_patient_ids = matched_pt
                    print(self.matching_patient_ids)
                    print(self.encoding_to_return)
            else:
                print("Empty Database")
                self.matching_patient_ids = [0]
                print(self.matching_patient_ids)         
        print("Final results:")
        print(self.faces_in_img_err)
        print(self.matching_patient_ids)
        print(self.encoding_to_return)

#Server class to handle individual requests on single thread
class SSL_TCPServer(TCPServer):

    #Constructor - binds to port using secure connection
    def __init__(self,
                 server_address,
                 RequestHandlerClass,
                 certfile,
                 keyfile,
                 ssl_version=ssl.PROTOCOL_TLSv1,
                 bind_and_activate=True):
        try:
            TCPServer.__init__(self, server_address, RequestHandlerClass, bind_and_activate)
            self.certfile = certfile
            self.keyfile = keyfile
            self.ssl_version = ssl_version
            self.error = "none"
        except OverflowError:
            print("OverflowError: please check Wi-Fi connection")
            self.error = "bad port, closing"
        except OSError:
            print("OSError: please check Wi-Fi connection")
            self.error = "bad host, closing"    

    def get_request(self):
        newsocket, fromaddr = self.socket.accept()
        connstream = ssl.wrap_socket(newsocket,
                                 server_side=True,
                                 certfile=self.certfile,
                                 keyfile=self.keyfile,
                                 ssl_version=self.ssl_version)
        print(connstream)
        return connstream, fromaddr

#Thread wrapper to serve clients across multiple ports, allows multiple client devices to analyze images on multiple threads
class SSL_ThreadingTCPServer(ThreadingMixIn, SSL_TCPServer):

    #Constructor, initiates server object instance on new thread
    def __init__(self, host_port_tuple, certfile, keyfile):
        SSL_TCPServer.__init__(self, host_port_tuple, self.IncomingHandler, certfile, keyfile)

    # Instantiates for each request via constsructor
    #Receives input stream from client device connected on new thread, processes input, generates and sends output stream
    class IncomingHandler(StreamRequestHandler):  

        def handle(self):
            #Output flag is first byte received
            optOut = self.connection.recv(1);
            print("Opt Out: ")
            print(optOut)
            #Receive JPG image in 4-byte structure, image size is first 4 bytes
            buf = b''
            while len(buf) < 4:
                buf += self.connection.recv(4 - len(buf))
            size = struct.unpack('!i', buf)[0]
            print('receiving {} bytes'.format(size))
            #Sends error code if no image received
            out_buffer = str('') 
            if(size == 0):  
                out_buffer += '\n'
                out_buffer += '\n'
                out_buffer += 'X'
                out_buffer += '\n'
            #Writes incoming image to JPG file if the image data exists
            else:
                newfilestring = 'test file {}.jpg'.format(time.time())
                with open(newfilestring, 'wb') as img:
                    while True:
                        try:
                            data = self.connection.recv(1024)
                            if not data:
                                print("connection closed")
                                self.connection.close()
                                break
                            else:
                                img.write(data)
                                size -= len(data)
                                if size <= 0:
                                    break
                        except socket.error as e:
                            if e.args[0] == errno.EWOULDBLOCK:
                                print ('EWOULDBLOCK')
                            else:
                                print (e)
                                print("breaking, shutting down")
                                break
                #Sends image read and write confirmation message
                out_buffer += "Image received successfully"
                out_buffer += '\n'
                picture = Image.open(newfilestring)
                width, height = picture.size
                #Rotates image if taken in landscape
                if(width > height):
                    picture.rotate(270).save(newfilestring)
                    
                #Calls facial recognition algorithm via new face comparison object
                comparison = face_comparison(newfilestring,optOut)
                print(comparison.encoding_to_return)
                #Sends number of faces detected in image
                out_buffer += str(comparison.faces_in_img_err)
                out_buffer += '\n'
                #Sends list of matching patient IDs
                for z in range(comparison.matching_patient_ids.__len__()):
                    out_buffer += str(comparison.matching_patient_ids[z])
                    out_buffer += '\n'
                out_buffer += 'X'
                out_buffer += '\n'
                #Sends encoding to return if encoding is processed, otherwise sends error code
                if isinstance(comparison.encoding_to_return, int):
                    print(comparison.encoding_to_return)
                    out_buffer += str(comparison.encoding_to_return)
                else:
                    if type(comparison.encoding_to_return).__module__ == np.__name__:
                        for x in range (128):
                            # for y in 4:
                            out_buffer += str(comparison.encoding_to_return[x])
                            out_buffer += '\n'
                out_buffer += '\n'
                #Deletes saved image - no patient images permanently saved
                try:
                    os.remove(newfilestring)
                #Stops server if image deletion fails, ensures images are not saved
                except OSError:
                    print("breaking, shutting down")
                    break #if desired
                    #pass #if desired
            #Sends complete buffer to client device
            self.connection.send(bytes(out_buffer, 'UTF-8'))
            print(out_buffer)
            
        #Required definitions
        def filter(self, data):
            pass

        #Required definitions    
        def write(self, data):
            pass

#Program implementation
#Opens json file to read SSL certificate and key file
#Serves multithreaded TCP server on specified socket
#Socket connections validate SSL, bind to a port, read and write data, process images and faces
if __name__ == "__main__":
    with open("C:\Server\ImageService\config.json", 'r') as jfile:
        confjson = json.load(jfile)
    certfile = "cert.pem"
    keyfile = "private.key"
    
    while(1): #Restart server after shutting down to restore function, if desired
        # server = SSL_ThreadingTCPServer(('localhost', confjson.get('PORT', None)), # from JSON, if desired
        server = SSL_ThreadingTCPServer(('192.168.1.109', 5063), # direct, if desired
                                certfile,
                                keyfile)
        try:
            print("Starting server now")
            server.serve_forever()
            print("Successfully started server")    
        except ValueError:
            print("please check Wi-Fi connection")
        finally:
            print("Server shutting down")
            server.shutdown()
            server.server_close()
            time.sleep(1); #sleep before attempting to restart server, if desired