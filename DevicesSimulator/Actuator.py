class Actuator:
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

    def isOn(self):
        return self.state == "ON"

    def isOff(self):
        return self.state == "OFF"
