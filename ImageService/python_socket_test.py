#See import notes in SocketServer.py
from SocketServer import face_comparison,SSL_ThreadingTCPServer
import numpy as np
import time

#Tests for SocketServer.py functions

#Testing class for face_compar functions
class face_comparisonTest:
    
    #Test for find_matches function of face_comparison class
    class face_comparison_find_matchesTest:
        #Can be the same for each
        #Sample image file location
        search_pt_pic = 'C:\\Users\\medley\\Desktop\\p\\theo_pic.jpg'
        pt_id = []
        all_face_encodings = []
        faces_in_img_err = int()
        encoding_to_return = []
        matching_patient_ids = []
        def __init__(self,search_face_encoding,optOut):
            self.faces_in_img_err = 1
            face_comparison.search_pt_pic = self.search_pt_pic
            self.search_face_encoding = search_face_encoding
            face_comparison.search_face_encoding = search_face_encoding
            face_comparison.__find_matches__(self,optOut)
            #print("Internal test result:")
            #print(self.faces_in_img_err)
            #print(self.matching_patient_ids)
            #print(self.encoding_to_return)
    
    
    #Test for constructor init function of face_comparison class
    class face_comparison_initTest:
        # can be the same for each
        pt_id = []
        all_face_encodings = []
        faces_in_img_err = []
        encoding_to_return = []
        matching_patient_ids = []
        def __init__(self, search_pt_pic,optOut):
            # different for each test
            face_comparison.all_face_encodings = []
            result = face_comparison(search_pt_pic,optOut)
            self.faces_in_img_err = result.faces_in_img_err
            self.encoding_to_return = result.encoding_to_return
            self.matching_patient_ids = result.matching_patient_ids
            #print("Post")
            #print(self.faces_in_img_err)
            #print(self.matching_patient_ids)
            #print(self.encoding_to_return)

#Testing for SSL_TCPServer and SSL_ThreadingTCPServer classes

class SocketServerTest:
    
    #Test for find_matches function of face_comparison class
    def __init__(self, host, port,certfile,keyfile):
        self.result = SSL_ThreadingTCPServer((host,port),
        certfile,
        keyfile)
        print("Starting server now")
        self.error = self.result.error
        
#Test image file locations
search_pt_pic_good_1 = 'C:\\Users\\medley\\Desktop\\p\\theo_pic.jpg'
search_pt_pic_bad_1 = ''
search_pt_pic_bad_2 = 'C:\\Images\\Input\\harrymeghan.jpg'
search_pt_pic_bad_3 = 'C:\\Images\\Input\\ranger.jpg'

#Test correct encoding format
search_face_encoding_good_1 = np.array([-0.15382788 , 0.00816037 , 0.0538962 , -0.05175451 , -0.14083922 , 0.07377105,
-0.10023099 , -0.04121803 , 0.1347629   , -0.1213267  , 0.14709429  , 0.09005648,
-0.15921442 , -0.01715159 , -0.05823774 , 0.07480818  , -0.10787214 ,- 0.07707427,
-0.04753904 , -0.09108765 , -0.01276829 , 0.04771585  , -0.0127448  , 0.0270322,
-0.10044438 , -0.33767584 , -0.08319399 , -0.08555525 , 0.1344786   , -0.0822144,
-0.071601   , -0.0571208  , -0.17422314 , -0.00684046 , 0.0257952   , 0.18566766,
-0.00146725 , -0.03476365 , 0.15320933  , -0.00143736 , -0.12871712 , -0.03651862,
 0.03911775 , 0.21156368  , 0.15555206  , 0.022697    , 0.10007614  , -0.0955667,
 0.17104557 , -0.23166944 , 0.07824943  , 0.08321585  , 0.08797862  , 0.05046749,
 0.05812386 , -0.19110918 , -0.04029935 , 0.18673098  , -0.12307521 , 0.14690334,
 0.00511312 , 0.01165629  , -0.02542832 , -0.00904295 , 0.23770514  , 0.10324246,
-0.13781756 , -0.20774628 , 0.16817731  , -0.20280546 , 0.03702178  , 0.10769756,
-0.05422088 , -0.18155663 , -0.1997437  , 0.11683324  , 0.50590283  , 0.18388778,
-0.11011137 , 0.02309527  , -0.13137795 , -0.11061931 , 0.06805475  , 0.072898,
-0.07072275 , 0.04825223  , -0.06043788 , 0.07611954  , 0.27555624  , 0.03084761,
0.05009493  , 0.1712113   , 0.02884733  , 0.11866401  , -0.00502058 , 0.0857092,
-0.15470186 , 0.02421967  , -0.08775298 , -0.01396845 , 0.00230809  , - 0.09263307,
0.10649296  , 0.08512437  , -0.15350103 , 0.1708698   , -0.04955552 , - 0.03566932,
0.01902231  , 0.03205912  , -0.12246352 , -0.0460559  , 0.20764986  , -0.2675935,
0.17459634  , 0.18082869  , 0.08673508  , 0.18978776  , 0.12260341  , 0.082412,
0.08611859  , -0.03215224 , -0.12480718 , -0.01073789 , -0.03434595 , -0.12347004,
0.02106265  , 0.01916024])

#Test incorrect encoding format
search_face_encoding_bad_1 = np.array([2000000,2000000,2000000,2000000,2000000,2000000,2000000,
                              2000000,2000000,2000000,2000000,2000000,2000000,2000000,
                              2000000,2000000,2000000,2000000,2000000,2000000,2000000,
                              2000000,2000000,2000000,2000000,2000000,2000000,2000000,
                              2000000,2000000,2000000,2000000,2000000,2000000,2000000,
                              2000000,2000000,2000000,2000000,2000000,2000000,2000000,
                              2000000,2000000,2000000,2000000,2000000,2000000,2000000,
                              2000000,2000000,2000000,2000000,2000000,2000000,2000000,
                              2000000,2000000,2000000,2000000,2000000,2000000,2000000,
                              2000000,2000000,2000000,2000000,2000000,2000000,2000000,
                              2000000,2000000,2000000,2000000,2000000,2000000,2000000,
                              2000000,2000000,2000000,2000000,2000000,2000000,2000000,
                              2000000,2000000,2000000,2000000,2000000,2000000,2000000,
                              2000000,2000000,2000000,2000000,2000000,2000000,2000000,
                              2000000,2000000,2000000,2000000,2000000,2000000,2000000,
                              2000000,2000000,2000000,2000000,2000000,2000000,2000000,
                              2000000,2000000,2000000,2000000,2000000,2000000,2000000,
                              2000000,2000000,2000000,2000000,2000000,2000000,2000000,
                              2000000,2000000])


print("Test 1")
optOut = 0
result = face_comparisonTest.face_comparison_find_matchesTest(search_face_encoding_good_1,optOut)
print("External test results:")
print(result.faces_in_img_err)
print(result.encoding_to_return)
print(result.matching_patient_ids)
#Test correct number of faces identified
assert result.faces_in_img_err == 1
#Test correct encoding format
assert result.encoding_to_return.shape == (128,)
#Test correct patient ID match
assert result.matching_patient_ids == [14411]

print("Test 2")
result = face_comparisonTest.face_comparison_find_matchesTest(search_face_encoding_bad_1,optOut)
#Test correct number of faces identified
assert result.faces_in_img_err == 1
#Test correct encoding format
assert result.encoding_to_return.shape == (128,)
#Test correct patient ID match
assert result.matching_patient_ids == [0]

print("Test 3")
result = face_comparisonTest.face_comparison_initTest(search_pt_pic_good_1,optOut)
#Test correct number of faces identified
assert result.faces_in_img_err == 1
#Test correct encoding format
assert result.encoding_to_return.shape == (128,)
#Test correct patient ID match
assert result.matching_patient_ids == [14411]

print("Test 4")
result = face_comparisonTest.face_comparison_initTest(search_pt_pic_bad_1,optOut)
#Test correct number of faces identified
assert result.faces_in_img_err == 3
print(np.array(result.encoding_to_return).size)
#Test correct encoding format
assert np.array(result.encoding_to_return).size == 1
#Test correct patient ID match
assert result.matching_patient_ids == [0]

print("Test 5")
result = face_comparisonTest.face_comparison_initTest(search_pt_pic_bad_2,optOut)
#Test correct number of faces identified
assert result.faces_in_img_err == 2
print(np.array(result.encoding_to_return).size)
#Test correct encoding format
assert np.array(result.encoding_to_return).size == 1
#Test correct patient ID match
assert result.matching_patient_ids == [0]

print("Test 6")
result = face_comparisonTest.face_comparison_initTest(search_pt_pic_bad_3,optOut)
#Test correct number of faces identified
assert result.faces_in_img_err == 0
print(np.array(result.encoding_to_return).size)
#Test correct encoding format
assert np.array(result.encoding_to_return).size == 1
#Test correct patient ID match
assert result.matching_patient_ids == [0]


#Sample good and bad host and port inputs
good_host = 'localhost'
good_port = 5063
bad_host = '192.168.1.107'
bad_port = 999999

#Sample good and bad cert and key file inputs
good_certfile = "cert.pem"
bad_certfile = "no_cert.pem"
bad_certfile_2 = "bad_cert.pem"
good_keyfile = "private.key" 
bad_keyfile = "no_key.key"
bad_keyfile_2 = "bad_key.key"

#Test response to good and bad host and port inputs
print("Test 7: Bad port")
bad_server = SocketServerTest(good_host, bad_port,good_certfile,good_keyfile)
time.sleep(2)
assert bad_server.error == "bad port, closing"
#bad_server.result.shutdown()
bad_server.result.server_close()
print("Test 8: Bad host")
bad_server2 = SocketServerTest(bad_host, good_port,good_certfile,good_keyfile)
time.sleep(2)
assert bad_server2.error == "bad host, closing"
bad_server2.result.server_close()
print("Test 9: Bad host and bad port")

bad_server3 = SocketServerTest(bad_host, bad_port,good_certfile,good_keyfile)
time.sleep(2)
assert bad_server3.error == "bad port, closing"
bad_server3.result.server_close()

print("Server Tests Complete")

#Test response to good and bad cert and key file inputs
print("Please use the following server configurations to test clients")
print("Test 10")
bad_server4 = SocketServerTest(good_host, good_port,bad_certfile,good_keyfile)
input("Please press Enter to continue:")
bad_server4.result.server_close()

print("Test 11")
bad_server5 = SocketServerTest(good_host, good_port,bad_certfile,good_keyfile)
time.sleep(2)
input("Please press Enter to continue:")
bad_server5.result.server_close()

print("Test 12")
bad_server6 = SocketServerTest(good_host, good_port,bad_certfile_2,good_keyfile)
time.sleep(2)
input("Please press Enter to continue:")
bad_server6.result.server_close()

print("Test 13")
bad_server7 = SocketServerTest(good_host, good_port,good_certfile,bad_keyfile)
time.sleep(2)
input("Please press Enter to continue:")
bad_server7.result.server_close()

print("Test 14")
bad_server8 = SocketServerTest(good_host, good_port,good_certfile,bad_keyfile_2)
time.sleep(2)
input("Please press Enter to continue:")
bad_server8.result.server_close()

print("Test 15")
good_server = SocketServerTest(good_host, good_port,good_certfile,good_keyfile)
time.sleep(2)
input("Please press Enter to continue:")
good_server.result.server_close()

print("All tests complete")
