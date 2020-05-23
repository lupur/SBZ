class RainSensor:
    def __init__(self, serialNumber):
        self.serialNumber = serialNumber
        self.raining = False
        self.status = 'OK'

    def isRaining(self):
        self.raining = True

    def isNotRaining(self):
        self.raining = False

    def statusToError(self):
        self.status = "ERROR"

    def statusToOk(self):
        self.status = "OK"
