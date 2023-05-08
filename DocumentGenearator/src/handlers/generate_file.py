from abc import ABC

from cleanapi.server import BaseHandler
import json
from src.logic.document_creator import generate

url_tail = '/generate_document'


class Handler(BaseHandler, ABC):
    async def post(self):
        try:
            body_json_dict = json.loads(self.request.body)
            generate(body_json_dict)
            self.set_status(200)
            self.write({'status': 'SUCCESS'})
        except Exception as ex:
            print(ex)
            self.set_status(500)
            self.write(ex)


