from cleanapi import server
import os

url_tail = ''

server.start('http', int(os.environ['PORT']), '', 'src/handlers', 'src', path_to_log='src/log')