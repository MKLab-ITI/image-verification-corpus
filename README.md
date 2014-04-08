image-verification-corpus
=========================

This contains an evolving dataset of fake and real images shared in social media. The purpose of the dataset is the development of a corpus that would be used for classifying new social content that contains images.

The dataset consists of two files:

set_images.txt: File that contains fake and real images as they were verified by online sources. These images were used to create our dataset. The file contains the image_id field used as a reference for each image, the image_url field that presents the online url of the image, the annotation that declares the veracity of the image and the event that the image comes from.

tweets_images.txt: File that contains the tweets used to form the dataset and the associated images they contain. This file contains the tweet_id field that presents the id of each tweet, the image_id that presents the reference id of the associated image, the annotation that declares the veracity of each tweet and the event that the tweet comes from. 

The project https://github.com/socialsensor/computational-verification implements a framework that uses the corpus. If you use this dataset, you should include this link to your work. 
