import React, { useEffect, useRef, useState } from 'react';
import ImageUploader from 'react-images-uploading';
import './Imageuploader.scss';
import axios from 'axios';
import { ICityCitizenComplaint } from 'app/shared/model/city-citizen-complaint.model';
import { ICityCitizenPhoto } from 'app/shared/model/city-citizen-photo.model';
import { indexOf } from 'lodash';

const complaintimage: ICityCitizenPhoto = {};

const ImageUpload = ({ picture }: any) => {
  let tab = [];
  const [selectedImages, setSelectedImages] = useState([]);
  const [image, setImages] = useState<ICityCitizenPhoto>(complaintimage);
  const [idimage, setidimage] = useState([]);

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
      };
    }

    const imagesArray = selectedFilesArray.map((file: any) => {
      return URL.createObjectURL(file);
    });
    setSelectedImages(previousImages => previousImages.concat(imagesArray));
    // FOR BUG IN CHROME
    event.target.value = '';
  };

  useEffect(() => {}, [image]);

  function deleteHandler(image) {
    setSelectedImages(selectedImages.filter(e => e !== image));
    URL.revokeObjectURL(image);
  }

  useEffect(() => {
    if (image.image != null) {
      axios
        .post('http://localhost:8080/api/city-citizen-photos', image)
        .then(response => {
          console.log(response);
          const imageid = response.data.id;
          setidimage([...idimage, imageid]);
          console.log(idimage);
        })

        .catch(error => console.log(error));
    }
  }, [image]);

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
    </section>
  );
};
export default ImageUpload;
