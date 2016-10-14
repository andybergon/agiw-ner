# agiw-ner
Andrea Bergonzo & Luca Massuda (https://github.com/Massuda) Chiara Lombardi Simone Laudato
## PageDownloader.java
Needs:
*bingKey*
*peoplePath*=/path/to/people/people.txt, path of the file that contains the names to search on Bing
*storagePath*=/path/to/storage/, path of the folder that will contain the downloaded pages

## PageConverter.java
Needs:
*storagePath*=/path/to/storage/, path of the folder that contains the downloaded pages
*json-path*=/path/to/jsons/, path to the folder that contains the folders of the people, each one containing some jsons of the downloaded pages
*ner-path*=/home/andybergon/clas-ner/, path to the folder that contains the folders of the people, each one containing some jsons of NER applied on the downloaded pages

## AlchemyAPI
We use AlchemyAPI (Entity Extraction)[http://www.alchemyapi.com/api/entity-extraction] for NER extraction from the downloaded pages.

## Regex
TBC