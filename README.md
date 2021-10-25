# Watuku

[![Build Status](https://img.shields.io/badge/platform-Android-green.svg)](http://developer.android.com/index.html)

## _Note_
This is a prototype application because at the time we are not connect it to cloud, as it is not yet finished.

## Overview
Watuku is an application aimed to be used by university students, researchers, and professionals for mineral detection, intended to be fast, cost-saving, and accurate. This application use machine learning that is installed with the application on the phone to detect the mineral from an image taken from camera or chosen from gallery and then the cloud will provide the data of the detected mineral.

## Machine Learning
Build using Tensor Flow you can find it here -> [Watuku TF Model](https://github.com/ezoizy/Mineral_Model)

## Documentation
Here's the steps to follow if you want to replicate the project by your own
- Download the Dataset from kaggle.com and process dataset on the notebook
- Make an auxiliary image folder from dataset because the dataset are less from the requirement that needed
- Create the training and validation batch using train generator
- Create the label by using train generator function
- Using transfer learning model inceptionresnetv2 
- Training the model
- Validating the model
- Use converter to convert keras model into tflite model
- Move tflite model into model folder in android and the label into assets in android project
- User can take a picture or select it from gallery and the model will process it, the result will be a label
- Detail page will provide information about the mineral detected from the label
