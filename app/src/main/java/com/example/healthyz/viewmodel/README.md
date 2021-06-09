# ViewModel 

The beauty of the ViewModel is that it acts as an intermediary between the Model and the View. In this app, there are multiple data sources, namely the local Room database and the remote cloud database. The ViewModel abstracts away all of the details about the sources of data, allowing the View to be completely oblivious, which is a huge advantage. Below is an image showing the high level memory model. Every level depends only on the level below it. 

<img src="/images/Memory Model.png" height=700> 
