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
        <input type="file" name="images" onChange={onSelectFile} multiple accept="image/png , image/jpeg, image/webp" />
      </label>
      <br />

      <input type="file" multiple />

      {selectedImages.length > 0 &&
        (selectedImages.length > 10 ? (
          <p className="error">
            You can't upload more than 10 images! <br />
            <span>
              please delete <b> {selectedImages.length - 10} </b> of them{' '}
            </span>
          </p>
        ) : (
          <button
            className="upload-btn"
            onClick={() => {
              console.log(selectedImages);
            }}
          >
            UPLOAD {selectedImages.length} IMAGE
            {selectedImages.length === 1 ? '' : 'S'}
          </button>
        ))}

      <div className="images">
        {selectedImages &&
          selectedImages.map((image, index) => {
            return (
              <div key={image} className="image">
                <img src={image} height="200" alt="upload" />
                <button onClick={() => deleteHandler(image)}>delete image</button>
              </div>
            );
          })}
      </div>
    </section>
  );
};
export default ImageUpload;
