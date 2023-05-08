from abc import ABC

from cleanapi.server import BaseHandler
import os

url_tail = '/get-file'


class Handler(BaseHandler, ABC):
    async def get(self):
        try:
            print("file requested")

            self.set_header('Content-type', 'application/octet-stream')
            self.set_status(200)
            test_path = os.path.join('src/samples',
                                     f"{int(self.request.arguments.get('id')[0])}.docx")
            test_file = open(test_path, "rb")
            self.write(test_file.read())
            test_file.close()
            print("file sent")
        except Exception as ex:
            print(ex)
            self.set_status(500)
            self.write(ex)
