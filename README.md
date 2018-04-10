# Hachy-android
Hahcy uses machine learning to candle eggs with a <b>phone</b> and a <b>Hachy Box</b>:
![screenshot][userImage]


## Table of Content
* [Concept](#concept)
	* Target Audience
	* Business Plan
	* Cooperation

* Project Demo
	* Hachy box Setup
	* Install App
	* App Demo & Use Procedure
	* [Web Client Demo & Use Procedure](web-client-demo-&-use-procedure)
* Architecture 
	* How it works
	* Azure Usage
	* Egg Development Data selection
* References
	* [Libraries](libraries)
	* [Dependencies](#dependencies)
	* [Author and License](#author-and-license)

Hachy-android candles your egg using an app and a cardbox

## Concept
With the rise of Machine Learning and Image Recognition technologies, the market has seen useful agricultural tools such as [Banna Freshness Analyzer][bananaAnalyize], a [Cucumber Sorter][cucumberSorter] and so on. However, there lacks a tool for _____ million egg farmers in USA and ____ in China for egg checking. 

In fact, 


The vision of our team is building an autonomous egg incubating tool that allows no human involvement in the incubating, candling and hatching of eggs. Hachy is considered as the first stage that enables autonomous egg development identification and tracking. 

### Target Audience
### Business Plan
### Cooperation


## Architecture
### How it Works
### Azure Usage
### Egg Development Data Selection

1. Purchase or make a cardbox of the following specs: 
![boxdesign](https://user-images.githubusercontent.com/7799433/38463598-7d6136e0-3acc-11e8-8c3c-4e20f85eb677.png)

## install app
#### option 1: Clone this repo and build it yourself
`$git clone https://github.com/hachyEgg/hachy-android`
Open up Android Studio->Open->find this folder->open

#### Option 2: Install apk from project's debug folder

#### Option 3: Download at http://hachy.azurewebsites.net/download

### Requirement:
 * Android Studio 3.0.0
 * Android 4.4 KitKat, API Level 19
 * 1G of allowcated ram

### Permission

## App Demo & Use Procedure
2. insert the device into the cardbox, A completed set up would look like this:
(insert image)

3. insert egg, close egg and turn on the light source, observe that the phone screen will show a the egg:
(insert image)

4. open app, login, and choose either to take picture or import existing egg picture: 

 Sign in                   |  Egg Catalogue                 |       Select Picture            |    Take Picture   
:-------------------------:|:------------------------------:|:-------------------------------:|:---------------------------:
![](screenshots/login.jpg) |![](screenshots/catalogue.jpg)  | ![](screenshots/imgpick.jpg)  | ![](screenshots/camshot.jpg) 
 
4. Press the button to capture the egg, the image will be persisted on the cloud and sent to Azure for analysis, Upon result complete, the status of the egg will change from "egg not found" to the deteremiend result

Determining Egg in Progress:|  Egg development initiated    |   Found Egg but No development  |    No Engg found
:-------------------------:|:------------------------------:|:-------------------------------:|:---------------------------:
![](screenshots/res_detecting.jpg)|![](screenshots/res_detected.jpg)| ![](screenshots/res_nodev.jpg)|![](screenshots/res_noegg.jpg) 

5. Navigate back to observe a catalogue of egg status


## Web-Client
As the phone is being mounted on the cardboard, it is a little hard to track egg status, the [Hachy Web Client](https://github.com/hachyEgg/hachy-web-dist) lets you see analysis data in realtime sync with the device remotely. As the phone maybe a little hard to check as it is been mounted.
![screenshot](https://user-images.githubusercontent.com/7799433/38462289-84e70c60-3ab2-11e8-9587-d5706807c0a2.png)

## Azure Usage

## Datasets: 
The datasets of which this modal is trained labeled in 4 different tags: 
 * egg_0 for egg with no development (no development)
 * egg_1 for egg with a visible zygote (developing)
 * egg_2 for egg with visible blood lines and other organs (mature)

 
## AUTHOR and LICENSE
Copyright (c) 2016 Eugene Wang [The MIT License (MIT)](LICENSE)

[comment]: # (Reference of images and links)
[userImage]:screenshots/banner.png
[bananaAnalyize]: https://www.ncbi.nlm.nih.gov/pubmed/25745200
[cucumberSorter]: https://persol-tech-s.co.jp/i-engineer/product/cucumber
