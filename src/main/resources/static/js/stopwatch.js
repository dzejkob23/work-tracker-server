var sw = {
  /* [INIT] */
  etime : null, // holds HTML time display
  erst : null, // holds HTML reset button
  ego : null, // holds HTML start/stop button
  timer : null, // timer object
  now : 0, // current timer
  init : function () {
    // Get HTML elements
    sw.etime = document.getElementById("sw-time");
    sw.erst = document.getElementById("sw-rst");
    sw.ego = document.getElementById("sw-go");

    // Attach listeners
    sw.erst.addEventListener("click", sw.reset);
    sw.erst.disabled = false;
    sw.ego.addEventListener("click", sw.start);
    sw.ego.disabled = false;
  },

  /* [ACTIONS] */
  tick : function () {
  // tick() : update display if stopwatch running

    // Calculate hours, mins, seconds
    sw.now++;
    var remain = sw.now;
    var hours = Math.floor(remain / 3600);
    remain -= hours * 3600;
    var mins = Math.floor(remain / 60);
    remain -= mins * 60;
    var secs = remain;

    // Update the display timer
    if (hours<10) { hours = "0" + hours; }
    if (mins<10) { mins = "0" + mins; }
    if (secs<10) { secs = "0" + secs; }
    sw.etime.innerHTML = hours + ":" + mins + ":" + secs;
  },

  start : function () {
  // start() : start the stopwatch

    sw.timer = setInterval(sw.tick, 1000);
    sw.ego.value = "Stop";
    sw.ego.removeEventListener("click", sw.start);
    sw.ego.addEventListener("click", sw.stop);
  },

  stop  : function () {
  // stop() : stop the stopwatch

    clearInterval(sw.timer);
    sw.timer = null;
    sw.ego.value = "Start";
    sw.ego.removeEventListener("click", sw.stop);
    sw.ego.addEventListener("click", sw.start);
  },

  reset : function () {
  // reset() : reset the stopwatch

    // Stop if running
    if (sw.timer != null) { sw.stop(); }

    // Reset time
    sw.now = -1;
    sw.tick();
  }
};

window.addEventListener("load", sw.init);