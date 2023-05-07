

# Plant Disease Detection Android App

<p align="center">
  <img src="https://user-images.githubusercontent.com/76757642/236619490-b5e09b73-0ef9-49a0-a625-c5d42c2d97e5.png"  
       style="width: 400px; height: 400px;" />
</p>



This is an Android app for detecting plant diseases using MobileNet V2 model and Our proposed model. The app is built using Kotlin programming language and follows MVVM design pattern with Repository pattern using Coroutines and Room Database. The app also integrates Firebase for backend, including 3 types of authentication and Firebase Cloud Messaging (FCM) for push notifications. Additionally, the app supports voice search in both Arabic and English languages.

## Application Demo
### Check out this [Demo](https://youtu.be/sk_WSSBfKB8) to see the Application in action!


## Features

- Detects plant diseases using MobileNet V2 model and Our proposed model
- Provides tips for users based on the classification of the input image
- Supports voice search in Arabic and English languages
- Includes 3 types of authentication through Firebase (email/password, Google, and phone)
- Uses Firebase Cloud Messaging (FCM) for push notifications
- Follows MVVM design pattern with Repository pattern using Coroutines and Room Database
- Videos for diseases & tips (English & Arabic)
- Language Support
- Posts added by the admin
- Sharing posts & classification results via other apps
- Ofline classification

## Some Screen Shots

![image](https://user-images.githubusercontent.com/76757642/236684476-65d67a8f-b3b5-4d0b-992d-2432f6823959.png)
![image](https://user-images.githubusercontent.com/76757642/236684671-3012cc8e-8a07-49cf-80d5-e388bdf074ea.png)



<!-- <p align="center">


<img src=https://user-images.githubusercontent.com/76757642/236683685-38d0f509-8c8d-4be3-a124-3f675ba5f14c.png
      style="width: 200px; height: 400px;" />
<img src=https://user-images.githubusercontent.com/76757642/236683695-e780c47f-3454-4586-b4ff-004b12a56c99.png
     style="width: 200px; height: 400px;" />
<img src=https://user-images.githubusercontent.com/76757642/236683710-167e3341-fe85-47f4-b6d5-8b2c9b10f448.png
      style="width: 200px; height: 400px;" />
<img src=https://user-images.githubusercontent.com/76757642/236683747-f52e6255-8318-404b-8942-5abea4f2c8a3.png
      style="width: 200px; height: 400px;" />
<img src=https://user-images.githubusercontent.com/76757642/236683759-31c2acf0-e420-4e52-86a8-0e1bf59ae7d7.png
      style="width: 200px; height: 400px;" />
<img src=https://user-images.githubusercontent.com/76757642/236683769-7777f30d-edcb-4fef-99d2-40ec56225b03.png
      style="width: 200px; height: 400px;" />
<img src=https://user-images.githubusercontent.com/76757642/236683784-183c8166-3f9c-43e0-a172-62cb3084643f.png
      style="width: 200px; height: 400px;" />
<img src=https://user-images.githubusercontent.com/76757642/236683788-7079e8c1-4f57-481c-bdf1-55cfecece6eb.png
       style="width: 200px; height: 400px;" />
</p> -->


## Usage

Upon launching the app, users can either choose to capture an image of the plant or select an existing image from their device's gallery. Once an image is selected, the app uses MobileNet V2 model to detect if the input image is a plant or not. If it is a plant, the app then uses our proposed model to classify the plant disease and provides tips for users based on the classification. The app also supports voice search, allowing users to search for plant diseases using voice commands in both Arabic and English languages.

## Technologies Used

- Kotlin programming language
- python
- TF light
- Firebase for backend and authentication
- Firebase Cloud Messaging (FCM) for push notifications
- MVVM design pattern
- Repository pattern 
- Coroutines
- Room Database

## Architecture

![image](https://user-images.githubusercontent.com/76757642/236619134-90d778e1-0ada-4b2b-8ac1-a52ea84f01c0.png)

## Credits

### This app was developed by: 
- [Hady Atef](https://github.com/hady-o)
- [Hady Ahmed](https://github.com/HadyAhmed00)
- [Hady Ehab](https://github.com/HodBossHod)
- [Haithem Mahmoud](https://github.com/haitham2001)
- [Hany mohamed](https://github.com/HaniASU)
### as a Graduation project. 

### Our proposed model used in this app was trained on the Kaggle, Dell and other datasets.

## Contact

If you have any questions or suggestions regarding the app, please contact hadyatef70@gmail.com.
