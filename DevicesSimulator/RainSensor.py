class RainSensor:
    def __init__(self, serialNumber):
        self.serialNumber = serialNumber
        self.raining = False
        self.status = 'OK'

    def setRain(self):
        self.raining = True

    def setNoRain(self):
        self.raining = False

    def isItRaining(self):
        return self.raining

    def statusToError(self):
        self.status = "ERROR"

    def statusToOk(self):
        self.status = "OK"
