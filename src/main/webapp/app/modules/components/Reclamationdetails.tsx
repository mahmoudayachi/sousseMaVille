import React, { useState, useEffect } from 'react';
import './CityServiceCarte.scss';
import axios from 'axios';
import { Card, CardBody, CardSubtitle, CardText, CardTitle } from 'reactstrap';
import Button from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link, useLocation } from 'react-router-dom';
import './Reclamationdetails.scss';
import { Carousel, CarouselItem, CarouselControl, CarouselIndicators, CarouselCaption } from 'reactstrap';

const Reclamationdetails = () => {
  let imagetab = [];
  let location = useLocation();
  let state = location.state as any;
  const [activeIndex, setActiveIndex] = useState(0);
  const [animating, setAnimating] = useState(false);
  //{imagetab.map((image, id) => (
  //   <img className="imgg" src={image.toString()} />
  // ))}
  const next = () => {
    const nextIndex = activeIndex === state.cityCitizenPhotos.length - 1 ? 0 : activeIndex + 1;
    setActiveIndex(nextIndex);
  };
  const previous = () => {
    const nextIndex = activeIndex === 0 ? state.cityCitizenPhotos.length - 1 : activeIndex - 1;
    setActiveIndex(nextIndex);
  };
  const goToIndex = newIndex => {
    setActiveIndex(newIndex);
  };

  for (let i = 0; i < state.cityCitizenPhotos.length; i++) {
    const imagecontent = state.cityCitizenPhotos[i].image;
    const imagetype = state.cityCitizenPhotos[i].imageContentType;
    const imageurl = 'data' + ':' + imagetype + ';' + 'base64,' + imagecontent;
    imagetab.push(imageurl);
    console.log(imagetab);
  }

  const slides = imagetab.map(image => {
    return (
      <CarouselItem key={image}>
        <img className="slider-image" src={image.toString()} />
        <CarouselCaption captionText={image.caption} captionHeader={image.caption} />
      </CarouselItem>
    );
  });
  return (
    <>
      <div className="reclamation-details main-container">Reclamation details</div>
      <div className="main-container">
        <Carousel activeIndex={activeIndex} next={next} previous={previous}>
          <CarouselIndicators items={imagetab} activeIndex={activeIndex} onClickHandler={goToIndex} />
          {slides}
          <CarouselControl direction="prev" directionText="Previous" onClickHandler={previous} />
          <CarouselControl direction="next" directionText="Next" onClickHandler={next} />
        </Carousel>
        <div className="information">
          <span className="type">Categorie:</span>
          <span className="valeur"> {state.complaintCategory.name}</span>
          <br></br>
          <br></br>
          <span className="type">Description:</span>
          <span> {state.description}</span>
          <br></br>
          <br></br>
          <span className="type">Date d'envoi:</span>
          <span> {state.date}</span>
          <br></br>
          <br></br>
          <span className="type">Lieu:</span>
          <span> {state.address}</span>
        </div>
      </div>
    </>
  );
};
export default Reclamationdetails;
