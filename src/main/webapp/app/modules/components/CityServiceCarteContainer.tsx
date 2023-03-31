import React, { useState, useEffect } from 'react';
import Carte from './CityServiceCarte';
import axios from 'axios';
import './CityServiceCarteContainer.scss';
import { identity } from 'lodash';
import CityServiceCarte from './CityServiceCarte';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Réclamationboard from './Réclamationboard';

export const CityServiceCartecontainer = () => {
  const [services, setServices] = useState([]);
  useEffect(() => {
    let servicedata = [];
    axios
      .get('http://localhost:8080/api/city-services')
      .then(res => {
        servicedata = res.data;
        console.log(servicedata);
        setServices(servicedata);
      })
      .catch(err => {
        console.log(err);
        servicedata = [];
      });
  }, []);

  return (
    <section className="carte-section">
      {services.map((services, id) => (
        <CityServiceCarte servicedata={services} key={services.id} />
      ))}
    </section>
  );
};

export default CityServiceCartecontainer;
