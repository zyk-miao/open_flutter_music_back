import json
import os
from requests_toolbelt import MultipartEncoder

import requests

url = 'http://127.0.0.1:8700/api/addMusic'


def add_music(file, up):
    m = MultipartEncoder(
        fields={
            'file': (file, up, 'audio/' + file[file.find('.') + 1:])}
    )
    r = requests.post(url, headers={'Content-Type': m.content_type}, data=m, params={
        'artistName': file[0:file.find('.')].split('-')[0],
        'musicName': file[0:file.find('.')].split('-')[1],
        'fileName': file
    })
    print(r.json())
    if r.json()['code'] == '200':
        print(file + '成功')
        # success_num = success_num + 1i
    else:
        print(file + '失败')


if __name__ == '__main__':
    success_num = 0
    for root, _, files in os.walk('/run/media/zyk/normal/KwDownload/song/'):
        for f in files[0:]:
            upload_file = open(root + f, 'rb')
            add_music(f, upload_file)
    # add_music('未知-未知.flac'.strip(), open('/home/zyk/下载/789055412.flac', 'rb'))
