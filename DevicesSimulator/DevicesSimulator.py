import requests
from datetime import datetime
import time
import json
import threading
from http.server import HTTPServer, BaseHTTPRequestHandler


from MoistureSensor import MoistureSensor
from Actuator import Actuator
from RainSensor import RainSensor
from io import BytesIO

moistureReading = "MOISTURE"
rainReading = "RAIN"
statusReading = "STATUS"
stateReading = "STATE"

pump0 = Actuator("Pump0000")
rain0 = RainSensor("Rain0000")
moist01 = MoistureSensor("Moisture0000", 20)
moist02 = MoistureSensor("Moisture0001", 40)
valve01 = Actuator("Valve0000")
valve02 = Actuator("Valve0001")

pump1 = Actuator("Pump0010")
rain1 = RainSensor("Rain0010")
moist11 = MoistureSensor("Moisture0010", 50)
moist12 = MoistureSensor("Moisture0011", 40)
valve11 = Actuator("Valve0010")
valve12 = Actuator("Valve0011")

actuatorsDict = {
    "Pump0000": pump0,
    "Pump0010": pump1,
    "Valve0000": valve01,
    "Valve0001": valve02,
    "Valve0010": valve11,
    "Valve0011": valve12
}

class ActuationRequestServer(BaseHTTPRequestHandler):
    def do_POST(self):
        global actuatorsDict
        content_length = int(self.headers['Content-Length'])
        body = json.loads(self.rfile.read(content_length))
        actuator = actuatorsDict.get(body['serialNo'])
        if actuator:
            print("Received request: ", str(body))
            actuator.state = body['state']
        self.send_response(200)
        self.end_headers()

def actuatorServer():
    httpd = HTTPServer(('localhost', 8081), ActuationRequestServer)
    httpd.serve_forever()

def sendReading(serialNumber, name, value):
    url='http://localhost:8080/reading'
    header = {"Content-Type": "application/json"}
    data = {
            'serialNo': serialNumber,
            'name': name,
            'value': value,
            'timestamp': datetime.now().strftime("%Y-%m-%dT%H:%M:%SZ")
        }
    try:
        requests.post(url,data=json.dumps(data), headers=header)
    except:
        return

def simulateArray1():
    global pump0
    global rain0
    global moist01
    global moist02
    global valve01
    global valve02

    while True:
        sendReading(pump0.serialNumber, statusReading, pump0.status)
        sendReading(rain0.serialNumber, statusReading, rain0.status)
        sendReading(moist01.serialNumber, statusReading, moist01.status)
        sendReading(moist02.serialNumber, statusReading, moist02.status)
        sendReading(valve01.serialNumber, statusReading, valve01.status)
        sendReading(valve02.serialNumber, statusReading, valve02.status)
        time.sleep(5)

        sendReading(pump0.serialNumber, stateReading, pump0.state)
        sendReading(rain0.serialNumber, rainReading, rain0.raining)
        sendReading(moist01.serialNumber, moistureReading, moist01.moistureValue)
        sendReading(moist02.serialNumber, statusReading, moist02.moistureValue)
        sendReading(valve01.serialNumber, stateReading, valve01.state)
        sendReading(valve02.serialNumber, stateReading, valve02.state)
        time.sleep(5)


def simulateArray2():
    global pump1
    global rain1
    global moist11
    global moist12
    global valve11
    global valve12

    while True:
        sendReading(pump1.serialNumber, statusReading, pump1.status)
        sendReading(rain1.serialNumber, statusReading, rain1.status)
        sendReading(moist11.serialNumber, statusReading, moist11.status)
        sendReading(moist12.serialNumber, statusReading, moist12.status)
        sendReading(valve11.serialNumber, statusReading, valve11.status)
        sendReading(valve12.serialNumber, statusReading, valve12.status)
        time.sleep(5)

        sendReading(pump1.serialNumber, stateReading, pump1.state)
        sendReading(rain1.serialNumber, rainReading, rain1.raining)
        sendReading(moist11.serialNumber, moistureReading, moist11.moistureValue)
        sendReading(moist12.serialNumber, statusReading, moist12.moistureValue)
        sendReading(valve11.serialNumber, stateReading, valve11.state)
        sendReading(valve12.serialNumber, stateReading, valve12.state)
        time.sleep(5)

def simulateOneSet():
    global pump0
    global rain0
    global moist01
    global valve01

    rain0.setNoRain()
    # rain0.setRain()
    while True:
        sendReading(pump0.serialNumber, statusReading, pump0.status)
        sendReading(rain0.serialNumber, statusReading, rain0.status)
        sendReading(moist01.serialNumber, statusReading, moist01.status)
        sendReading(valve01.serialNumber, statusReading, valve01.status)
        time.sleep(1)

        sendReading(pump0.serialNumber, stateReading, pump0.state)
        sendReading(rain0.serialNumber, rainReading, rain0.raining)
        sendReading(moist01.serialNumber, moistureReading, moist01.moistureValue)
        sendReading(valve01.serialNumber, stateReading, valve01.state)
        time.sleep(1)

        if valve01.isOn():
            moist01.increaseMoisture()
        else:
            moist01.decreaseMoisture()

if __name__ == "__main__":

    thread1 = threading.Thread(target=actuatorServer)
    # thread2 = threading.Thread(target=simulateArray1)
    # thread3 = threading.Thread(target=simulateArray2)

    oneSetSimulation = threading.Thread(target=simulateOneSet)

    thread1.start()
    # thread2.start()
    # thread3.start()
    oneSetSimulation.start()
    try:
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        pass
