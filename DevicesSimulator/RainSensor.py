class RainSensor:
    def __init__(self, serialNumber):
        self.serialNumber = serialNumber
        self.raining = "OFF"
        self.status = 'OK'

    def setRain(self):
        self.raining = "ON"

    def setNoRain(self):
        self.raining = "OFF"

    def isItRaining(self):
        return self.raining == "ON"

    def statusToError(self):
        self.status = "ERROR"

    def statusToOk(self):
        self.status = "OK"
