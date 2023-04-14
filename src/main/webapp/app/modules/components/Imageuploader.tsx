import React, { useState } from 'react';
import ImageUploader from 'react-images-uploading';
import './Imageuploader.scss';

const ImageUpload = () => {
  const [selectedImages, setSelectedImages] = useState([]);
  const onSelectFile = event => {
    const selectedFiles = event.target.files;
    const selectedFilesArray = Array.from(selectedFiles);

    const imagesArray = selectedFilesArray.map((file: any) => {
      return URL.createObjectURL(file);
    });

    setSelectedImages(previousImages => previousImages.concat(imagesArray));

    // FOR BUG IN CHROME
    event.target.value = '';
  };

  function deleteHandler(image) {
    setSelectedImages(selectedImages.filter(e => e !== image));
    URL.revokeObjectURL(image);
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
            <div key={image} className="image">
              <img src={image} height="200" alt="upload" />
              <button onClick={() => deleteHandler(image)}>x</button>
            </div>
          );
        })}
      </div>
    </section>
  );
};
export default ImageUpload;
