import React, { useState } from 'react';
import ImageUploader from 'react-images-uploading';
import './Imageuploader.scss';
import axios from 'axios';
import { ICityCitizenComplaint } from 'app/shared/model/city-citizen-complaint.model';
import { ICityCitizenPhoto } from 'app/shared/model/city-citizen-photo.model';

const photo: ICityCitizenPhoto = {
  image: '',
};

const ImageUpload = () => {
  const [selectedImages, setSelectedImages] = useState([]);
  const [image, setImages] = useState<ICityCitizenPhoto>(photo);
  const onSelectFile = event => {
    const selectedFiles = event.target.files;
    const selectedFilesArray = Array.from(selectedFiles);

    const imagesArray = selectedFilesArray.map((file: any) => {
      return URL.createObjectURL(file);
    });

    setSelectedImages(previousImages => previousImages.concat(imagesArray));
    const array = Object.entries(selectedImages);
    const object = Object.fromEntries(array);
    let tab = [];
    tab.push(object);
    console;

    // for(let i=0;i<selectedImages.length;i++){
    //  setImages({...picture,image:selectedImages[i]})
    //  console.log(picture)
    //  data.append("image",picture.image)
    //}
    // console.log(data)

    //for(let i=0;i<selectedImages.length;i++){
    //  const chaine=selectedImages[i]
    // setImages({...picture,image:chaine})
    // const array=Object.entries(picture)
    // console.log(array)
    // }

    // FOR BUG IN CHROME
    event.target.value = '';
  };

  function deleteHandler(image) {
    setSelectedImages(selectedImages.filter(e => e !== image));
    URL.revokeObjectURL(image);
  }

  function afficher() {
    console.log();
  }
  function post(object) {
    axios
      .post('http://localhost:8080/api/city-citizen-photos', object)
      .then(response => console.log(response))
      .catch(error => console.log(error));
  }
  return (
    <section>
      <label className="uploadfile">
        Ajouter une photo
        <input type="file" name="image" onChange={onSelectFile} multiple accept="image/png , image/jpeg, image/webp" className="upload" />
      </label>
      <br />

      <div className="images">
        {selectedImages.map((image, index) => {
          return (
            <div>
              <div key={image} className="image">
                <img src={image} height="200" alt="upload" />
                <button onClick={() => deleteHandler(image)}>x</button>
              </div>

              <button onClick={post}>upload</button>
            </div>
          );
        })}
      </div>
    </section>
  );
};
export default ImageUpload;
