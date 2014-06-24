image-verification-corpus
=========================

This contains an evolving dataset of fake and real images shared in social media (twitter for now). The purpose of the dataset is the development of an open corpus that may be used for assessing online image verification approaches (based on tweet text and user features) and for building classifier for new content (currently tweets containing images).

The dataset consists of three files:
* set_images.txt: File that contains fake and real images that have been verified by online sources. These images were used to find tweets to build our dataset. The file contains the image_id field used as a reference for each image, the image_url field that presents the online url of the image, the annotation that declares the veracity of the image and the event that the image comes from.
* tweets_images.txt: File that contains the tweets used to form the dataset and the associated images they contain. This file contains the tweet_id field that presents the id of each tweet, the image_id that presents the reference id of the associated image, the annotation that declares the veracity of each tweet and the event that the tweet comes from. 
* tweets_images_update.txt: File that contains only the pure fake tweets from the previous file. Tweets with funny content or tweets declaring that their content is fake, have been removed from the dataset.
 
The [computational-verification](https://github.com/socialsensor/computational-verification) project implements a framework that uses the corpus. If you use this dataset and/or the linked framework in your research, please include the following reference in your work:

C. Boididou, S. Papadopoulos, Y. Kompatsiaris, S. Schifferes, N. Newman. Challenges of Computational Verification in Social Media. In Proceedings of SNOW II: Social News on the Web workshop, WWW'14 Companion.
