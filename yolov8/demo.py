from ultralytics import YOLO
# from ultralytics.yolo.utils.benchmarks import benchmark
import cv2
# Load a model
# model = YOLO("yolov8n.yaml") # build a new model from scratch
model = YOLO("E:/DeskTop/train45/weights/best.pt") # load a pretrained model (recommended for training)

# Use the model
# model.train(data="coco128.yaml", epochs=3,workers=0) # train the model,workers=0 if windows
# metrics = model.val() # evaluate model performance on the validation set
'''
results = model("bus.jpg") # predict on an image
print(results)
# '''
img_path = "E:/Python/yolov8/datasets/demo/valid/images"
results = model.predict(img_path,save=True,save_txt=True,classes=[0,1,2],conf=0.6)