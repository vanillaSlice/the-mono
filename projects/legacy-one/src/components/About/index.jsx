import React from 'react';

import Terminal from './Terminal';

import './index.css';

const About = () => (
  <div className="About">
    <h1>About, Skills and Interests</h1>
    <Terminal />
    <p>
      I&apos;m Mike, a software developer from St. Helens, England. I work across the full stack
      and I am always up for learning new things!
    </p>
    <h2>Background...</h2>
    <p>
      I developed an interest for programming whilst at college.
      This led me to study Computer Science at The University of Liverpool where
      I graduated in 2014.
    </p>
    <h2>I previously...</h2>
    <p>
      Worked as a Developer for <a href="https://www.jisc.ac.uk/">Jisc</a> on
      the <a href="https://www.jisc.ac.uk/historical-texts">Historical Texts</a> and <a href="https://www.jisc.ac.uk/journal-archives">Journal Archives</a> platforms.
      Working here exposed me to a lot of modern technology including Elasticsearch, MongoDB and
      AngularJS (although I&apos;m more of a React man myself).
    </p>
    <h2>I am currently...</h2>
    <p>
      Developing for <a href="https://upsideenergy.co.uk/">Upside Energy</a>.
    </p>
    <h2>When not programming I&apos;m...</h2>
    <p>
      Reading, baking, enjoying memes or making squealing sounds with a guitar.
    </p>
    <h2>Likes...</h2>
    <p>
      Stephen King, My Bloody Valentine (the band of course), and terrible films.
    </p>
    <h2>Dislikes...</h2>
    <p>
      Anything that isn&apos;t a Krispy Kreme.
    </p>
    <p className="easter-egg">
      ...hmmmm I wonder if this site has a Konami code Easter egg...
    </p>
  </div>
);

export default About;
