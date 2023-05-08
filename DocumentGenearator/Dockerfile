FROM python:3.9-slim


RUN mkdir gen
COPY requirements.txt gen/requirements.txt
RUN pip install -r gen/requirements.txt

ADD /src /gen/src
COPY server.py /gen/server.py
WORKDIR /gen

ENV PORT: 8085

CMD ["python", "-u", "server.py"]