from MoistureSensor import MoistureSensor
import requests
from datetime import datetime
import time
import json

moistureReading = "MOISTURE"
statusReading = "STATUS"

def sendReading(serialNumber, name, value):
    url='http://localhost:8080/reading'
    header = {"Content-Type": "application/json"}
    data = {
            'serialNo': serialNumber,
            'name': name,
            'value': value,
            'timestamp': datetime.now().strftime("%Y-%M-%dT%H:%M:%SZ")
        }

    requests.post(url,data=json.dumps(data), headers=header)

if __name__ == "__main__":
    sensor1 = MoistureSensor("Moisture0000", 50)

    try:
        while True:
            sendReading(sensor1.serialNumber, moistureReading, sensor1.moistureValue )
            sendReading(sensor1.serialNumber, statusReading, sensor1.status )
            sensor1.decreaseMoisture()
            time.sleep(10)
    except KeyboardInterrupt:
        pass
