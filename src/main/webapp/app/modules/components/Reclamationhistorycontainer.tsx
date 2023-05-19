import React, { useEffect, useState } from 'react';
import './Reclamationhistorycontainer.scss';
import Reclamationhistorycard from './Reclamationhistorycard';
import axios from 'axios';

const Reclamationhistorycontainer = () => {
  const [complaintdata, setcomplaintdata] = useState([]);
  useEffect(() => {
    let complaintdata = [];
    axios
      .get('http://localhost:8080/api/city-citizen-complaints?eagerload=true')
      .then(res => {
        complaintdata = res.data;
        console.log(complaintdata);
        setcomplaintdata(complaintdata);
      })
      .catch(err => {
        console.log(err);
        complaintdata = [];
      });
  }, []);

  return (
    <div className="history-container">
      {complaintdata.map((complaintdata, id) => (
        <Reclamationhistorycard complaintdata={complaintdata} key={complaintdata.id} />
      ))}
    </div>
  );
};
export default Reclamationhistorycontainer;
