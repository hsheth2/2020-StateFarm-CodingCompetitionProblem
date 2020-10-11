import React, { useEffect, useState } from "react";
import "./App.css";
import axios from "axios";
import { AgGridColumn, AgGridReact } from "ag-grid-react";

import "ag-grid-community/dist/styles/ag-grid.css";
import "ag-grid-community/dist/styles/ag-theme-alpine.css";

const csv_parse = require("csv-parse/lib/sync");

function DataViewer({ file }) {
  const [data, setData] = useState(null);

  useEffect(() => {
    axios.get(`/data/${file}.csv`).then((res) => {
      const csv = res.data;

      // Parse CSV data.
      const info = csv_parse(csv, {
        columns: true,
      });
      console.log(info);
      setData(info);
    });
  }, [file, setData]);

  if (!data) {
    return <p>Loading...</p>;
  }

  const columns = Object.keys(data[0]);

  return (
    <div className="ag-theme-alpine" style={{ height: 500, width: 900 }}>
      <AgGridReact rowData={data}>
        {columns.map(col => (
          <AgGridColumn key={col} field={col} sortable={true} filter={true}></AgGridColumn>
        ))}
      </AgGridReact>
    </div>
  );
}

function App() {
  return (
    <div className="App">
      <h1>State Farm 2020 - Data Browser</h1>

      <h2>Agents</h2>
      <DataViewer file="agents" />

      <h2>Claims</h2>
      <DataViewer file="claims" />

      <h2>Customers</h2>
      <DataViewer file="customers" />

      <h2>Vendors</h2>
      <DataViewer file="vendors" />
    </div>
  );
}

export default App;
