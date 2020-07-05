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

statusReading = "STATUS"

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

pump3 = Actuator("Pump0020")
rain3 = RainSensor("Rain0020")
moist21 = MoistureSensor("Moisture0020", 50)
moist22 = MoistureSensor("Moisture0021", 40)
valve21 = Actuator("Valve0020")
valve22 = Actuator("Valve0021")


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

def simulateArray2():
    global pump1
    global rain1
    global moist11
    global moist12
    global valve11
    global valve12

    sendReading(pump1.serialNumber, statusReading, "ERROR")
    time.sleep(5)
    sendReading(pump1.serialNumber, statusReading, "ERROR")
    time.sleep(5)
    sendReading(moist11.serialNumber, statusReading, "ERROR")
    sendReading(valve12.serialNumber, statusReading, "ERROR")
    sendReading(pump1.serialNumber, statusReading, "OK")
    time.sleep(5)
    sendReading(moist11.serialNumber, statusReading, "ERROR")
    sendReading(valve12.serialNumber, statusReading, "ERROR")
    sendReading(pump1.serialNumber, statusReading, "OK")
    time.sleep(5)
    sendReading(moist11.serialNumber, statusReading, "OK")
    sendReading(valve12.serialNumber, statusReading, "OK")
    sendReading(pump1.serialNumber, statusReading, "OK")
    time.sleep(5)
    sendReading(moist11.serialNumber, statusReading, "OK")
    sendReading(valve12.serialNumber, statusReading, "OK")
    sendReading(pump1.serialNumber, statusReading, "OK")
    time.sleep(5)
    sendReading(moist11.serialNumber, statusReading, "OK")
    sendReading(valve12.serialNumber, statusReading, "OK")
    sendReading(pump1.serialNumber, statusReading, "OK")
    time.sleep(5)
    sendReading(moist11.serialNumber, statusReading, "OK")
    sendReading(valve12.serialNumber, statusReading, "OK")
    sendReading(pump1.serialNumber, statusReading, "OK")
    time.sleep(5)
    sendReading(moist11.serialNumber, statusReading, "OK")
    sendReading(valve12.serialNumber, statusReading, "OK")
    sendReading(pump1.serialNumber, statusReading, "OK")

def simulateArray3():
    global pump2
    global rain2
    global moist21
    global moist22
    global valve21
    global valve22

    sendReading(moist01.serialNumber, statusReading, "ERROR")
    time.sleep(5)
    sendReading(moist01.serialNumber, statusReading, "ERROR")
    time.sleep(5)
    sendReading(moist01.serialNumber, statusReading, "ERROR")
    sendReading(valve02.serialNumber, statusReading, "ERROR")
    time.sleep(5)
    sendReading(moist01.serialNumber, statusReading, "OK")
    sendReading(valve02.serialNumber, statusReading, "ERROR")
    time.sleep(5)
    sendReading(moist01.serialNumber, statusReading, "OK")
    sendReading(valve02.serialNumber, statusReading, "OK")
    time.sleep(5)
    sendReading(moist01.serialNumber, statusReading, "OK")
    sendReading(valve02.serialNumber, statusReading, "OK")
    time.sleep(5)
    sendReading(moist01.serialNumber, statusReading, "OK")
    sendReading(valve02.serialNumber, statusReading, "OK")
    time.sleep(5)
    sendReading(moist01.serialNumber, statusReading, "OK")
    sendReading(valve02.serialNumber, statusReading, "OK")
    time.sleep(5)
    sendReading(moist01.serialNumber, statusReading, "OK")
    sendReading(valve02.serialNumber, statusReading, "OK")

if __name__ == "__main__":

    array2Simulation = threading.Thread(target=simulateArray2)
    array3Simulation = threading.Thread(target=simulateArray3)

    array2Simulation.start()
    array3Simulation.start()
    try:
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        pass
