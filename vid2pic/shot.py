from time import sleep

import requests
import numpy as np
import cv2
import datetime

url="http://192.168.8.1:8083/?action=snapshot"
SavePath="./Pictures"

def download_img() -> object:
    """
    从路由器上下载图像
    :return: opencv格式的图像
    """
    response = requests.get(url)
    data = response.content
    img1 = np.frombuffer(data, np.uint8)
    img_cv = cv2.imdecode(img1, cv2.IMREAD_ANYCOLOR)
    return img_cv



while True:
    image = download_img()
    cv2.imshow("frame", image)
    dt_ms = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S.%f')
    dt_msnodate = datetime.datetime.now().strftime('%H-%M-%S.%f')

    print(dt_ms)
    print(cv2.imwrite(f'{SavePath}\{dt_msnodate}.jpg', image))

    sleep(0.25)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break
cv2.destroyAllWindows()