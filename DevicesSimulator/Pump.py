class Pump:
    def __init__(self, serialNumber):
        self.serialNumber = serialNumber
        self.state = "OFF"
        self.status = 'OK'

    def turnOn(self):
        self.state = "ON"

    def turnOff(self):
        self.state = "OFF"

    def statusToError(self):
        self.status = "ERROR"

    def statusToOk(self):
        self.status = "OK"
