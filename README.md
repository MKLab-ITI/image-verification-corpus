image-verification-corpus
=========================

This contains an evolving dataset of fake and real posts with images shared in social media (twitter for now). The purpose of the dataset is the development of an open corpus that may be used for assessing online image verification approaches (based on tweet text and user features) and for building classifier for new content (currently tweets containing images).

The dataset consists of three files:
* set_images.txt: File that contains fake and real images that have been verified by online sources. These images were used to find tweets to build our dataset. The file contains the image_id field used as a reference for each image, the image_url field that presents the online url of the image, the annotation that declares the veracity of the image and the event that the image comes from.
* tweets_images.txt: File that contains the tweets used to form the dataset and the associated images they contain. This file contains the tweet_id field that presents the id of each tweet, the image_id that presents the reference id of the associated image, the annotation that declares the veracity of each tweet and the event that the tweet comes from. 
* tweets_images_update.txt: File that contains only the pure fake tweets from the previous file. Tweets with funny content or tweets declaring that their content is fake, have been removed from the dataset.
* tweets_event.txt: File that contains the tweets with fake content we used, but they are no longer online available, either because the user erased them or the user account is suspended. 

To use the corpus, just use the set_images.txt file with the verified images and one of the other files that contains the tweets as described above.

The [computational-verification](https://github.com/socialsensor/computational-verification) project implements a framework that uses the corpus. If you use this dataset and/or the linked framework in your research, please include the following reference in your work:

C. Boididou, S. Papadopoulos, Y. Kompatsiaris, S. Schifferes, N. Newman. Challenges of Computational Verification in Social Media. In Proceedings of SNOW II: Social News on the Web workshop, WWW'14 Companion.

The mediaeval2015 folder contains the version of the dataset provided for the Verifying Multimedia Use task in the context of MediaEval Workshop 2015. In folders devset and testset, you will find the tweet data shared for training and testing respectively. When organizing this task, we have also shared for each dataset's tweets, some features based on tweet and user characteristics and some forensic ones for the images which are associated with the tweets.

If you use this video dataset for your research, please include a citation to the following paper: Boididou, C., Papadopoulos, S., Zampoglou, M., Apostolidis, L., Papadopoulou, O., & Kompatsiaris, Y. (2018). [Detection and visualization of misleading content on Twitter](https://link.springer.com/article/10.1007/s13735-017-0143-x). International Journal of Multimedia Information Retrieval, 7(1), 71-86.

    @article{boididou2018detection,
      author = {Detection and visualization of misleading content on Twitter},
      title = {Boididou, Christina and Papadopoulos, Symeon and Zampoglou, Markos and Apostolidis, Lazaros and Papadopoulou, Olga and Kompatsiaris, Yiannis},
      journal = {International Journal of Multimedia Information Retrieval},
      volume={7},
      number={1},
      pages={71--86},
      year={2018},
      doi = "10.1007/s13735-017-0143-x",     
      publisher={Springer}
    }
