
# packages
import cv2
from cv2 import VideoWriter
from cv2 import VideoWriter_fourcc
import pyautogui as pg
import numpy as np
from ultralytics import YOLO
import time
import pandas as pd
from pynput.mouse import Button, Controller

mouse = Controller()
# create an output video stream

model = YOLO('yolov8n.pt')
video = VideoWriter('desktop.avi', VideoWriter_fourcc(*'MP42'), 7, (1366, 768))
while True:

    screenshot = cv2.cvtColor(np.array(pg.screenshot()), cv2.COLOR_RGB2BGR)




    results = model.predict(
    source=screenshot,
    #source='C:/Users/grizz/OneDrive/Pictures/3088.jpg',
    save = True,
    #stream=True,
    show = True,
    #save_txt = True,
    conf = 0.7

    )


   # lis = open("C:/Users/grizz/OneDrive/Desktop/groundfixing python ai/runs/detect/predict/labels/3088.txt" , "r").readlines()

    #print(lis)
        
    #cv2.imshow('Screenshot', screenshot)

    try:
        for r in results:
            biglist = []
            x= []
            y= []
            x2= []
            y2= []
            name = []
            for c in r.boxes.xyxyn   :
                #print(model.names[int(c)])
                i = -1
                for thing in c:
                    i = i+1
                    print(thing.item())
                    if i == 0:
                        x.append(float(thing.item()))
                    if i == 1:
                        y.append(float(thing.item()))
                    if i == 2:
                        x2.append(float(thing.item()))
                    if i == 3:
                        y2.append(float(thing.item()))
                        
                #print(x)
                #print(y)
                #print(x2)
                #print(y2)
            for c in r.boxes.conf:
                #print(model.names[int(c)])
                print(c)
            for c in r.boxes.cls:
                print(model.names[int(c)])
                name.append(model.names[int(c)])
                
                
            #x.append(biglist[0])
            #y.append(biglist[1])
            #x2.append(biglist[2])
            #y2.append(biglist[3])

            
            data = {'Name': [name],
            'x': [float(x[0])],
            'y': [float(y[0])],
            'x2': [float(x2[0])],
            'y2': [float(y2[0])]}

            

            df = pd.DataFrame(data)


            b = -1
            for i in x:
                
                b = b +1
                df2 = {'Name': name[b], 'x': x[b], 'y': y[b], "x2": x2[b], "y2" : y2[b]}
                df = df.append(df2, ignore_index = True)

            dfNameSorted = df[df['Name'] == 'person']
            df_closest = dfNameSorted.iloc[(df['x']-.5).abs().argsort()[:1]]

            

            print(f"Closest value: {df_closest} x: {df_closest['x']}" )
            #print(float(df_closest['x'].values[:1]))

            print(float(df_closest['x2'].values[:1]))
            print(float(df_closest['x'].values[:1]))
            middle = (float(df_closest['x2'].values[:1]) + float(df_closest['x'].values[:1])) /2
            print(f"\nMiddle: {middle}")

            if float(middle) > .50:
                print("ON RIGHT")

                turn = (abs(float(middle)) -.5) * 3000

                #if turn > 30:
                 #   turn = turn +100
                mouse.move(turn, 0)
                print(f"Turn{turn}")



            if float(middle) < .50:
                print("on left")

                turn = (abs(float(middle)) -.5) *3000
                #if turn > 30:
                 #   turn = turn +100
                mouse.move(turn, 0)
                print(f"Turn{turn}")



            #print(df)

            #df.to_csv(r'C:\Users\grizz\OneDrive\Desktop\groundfixing python ai\csv\csv.csv')  
            #time.sleep(0.1)
    except:
        print("NO PERSON DETECTED")
        #time.sleep(1)




cv2.waitKey(0)
# clean ups
cv2.destroyAllWindows()


