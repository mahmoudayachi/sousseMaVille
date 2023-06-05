import React, { useEffect, useState } from 'react';
import './Reclamationhistorycontainer.scss';
import Reclamationhistorycard from './Reclamationhistorycard';
import axios from 'axios';
import { useAppSelector } from 'app/config/store';

const Reclamationhistorycontainer = () => {
  const [complaintdata, setcomplaintdata] = useState([]);
  const account = useAppSelector(state => state.authentication.account);
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

  let array = [];
  array = complaintdata.filter(u => u.user == null || u.user.login == account.login);
  return (
    <div className="history-container">
      {array.map((complaintdata, id) => (
        <Reclamationhistorycard complaintdata={complaintdata} key={complaintdata.id} />
      ))}
    </div>
  );
};
export default Reclamationhistorycontainer;
