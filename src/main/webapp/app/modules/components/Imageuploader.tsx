import React, { useEffect, useState } from 'react';
import ImageUploader from 'react-images-uploading';
import './Imageuploader.scss';
import axios from 'axios';
import { ICityCitizenComplaint } from 'app/shared/model/city-citizen-complaint.model';
import { ICityCitizenPhoto } from 'app/shared/model/city-citizen-photo.model';
import { indexOf } from 'lodash';

const complaintimage: ICityCitizenPhoto = {
  image: '',
};

const ImageUpload = () => {
  const [selectedImages, setSelectedImages] = useState([]);
  const [image, setImages] = useState<ICityCitizenPhoto>(complaintimage);
  const onSelectFile = event => {
    const selectedFiles = event.target.files;

    const selectedFilesArray = Array.from(selectedFiles);

    var reader = new FileReader();
    for (let i = 0; i < selectedFilesArray.length; i++) {
      reader.readAsDataURL(selectedFiles[i]);
      reader.onload = function () {
        const base64data = reader.result.toString();
        const content = base64data.split(',', 2);
        const imagecontent = content[1];
        const type = base64data.split(';', 2);
        const typeimage = type[0].split(':')[1];
        setImages({ ...image, image: imagecontent.toString(), imageContentType: typeimage.toString() });

        console.log(typeimage);
      };
    }

    const imagesArray = selectedFilesArray.map((file: any) => {
      return URL.createObjectURL(file);
    });
    setSelectedImages(previousImages => previousImages.concat(imagesArray));
    // FOR BUG IN CHROME
    event.target.value = '';
  };

  let tabid = [];
  useEffect(() => {
    tabid.push(image.id);
    console.log(tabid);
    post();
  }, [image]);

  function deleteHandler(image) {
    setSelectedImages(selectedImages.filter(e => e !== image));
    URL.revokeObjectURL(image);
  }

  function post() {
    axios
      .post('http://localhost:8080/api/city-citizen-photos', image)
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
            <>
              <div key={image} className="image">
                <img src={image} height="200" alt="upload" />
                <button onClick={() => deleteHandler(image)}>x</button>
              </div>
            </>
          );
        })}
      </div>
      <div>
        <button onClick={() => post()}>send</button>
      </div>
    </section>
  );
};
export default ImageUpload;
