class MoistureSensor:
    def __init__(self, serialNumber, moistureValue):
        self.serialNumber = serialNumber
        self.moistureValue = moistureValue
        self.status = 'OK'

    def increaseMoisture(self):
        if self.moistureValue <= 95:
            self.moistureValue += 3

    def decreaseMoisture(self):
        if self.moistureValue >=5:
            self.moistureValue -= 3

    def statusToError(self):
        self.status = "ERROR"

    def statusToOk(self):
        self.status = "OK"
