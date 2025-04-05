/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Swen Kooij / Photonios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/*!
 * \class DndSimulatorDataTransfer
 *
 * \brief Re-implementation of the native \see DataTransfer object.
 *
 * \param data Optional: The data to initialize the data transfer object with.
 *
 * \see https://developer.mozilla.org/en-US/docs/Web/API/DataTransfer
 */
var DndSimulatorDataTransfer = function() {
    this.data = {};
};

/*!
 * \brief Controls the feedback currently given to the user.
 *
 * Must be any of the following strings:
 *
 * - "move"
 * - "copy"
 * - "link"
 * - "none"
 *
 * The default is "move".
 *
 * \see https://developer.mozilla.org/en-US/docs/Web/API/DataTransfer/dropEffect
 */
DndSimulatorDataTransfer.prototype.dropEffect = "move";

/*!
 * \brief Controls which kind of drag/drop operatins are allowed.
 *
 * Must be any of the following strings:
 *
 * - "none"
 * - "copy"
 * - "copyLink"
 * - "copyMove"
 * - "link"
 * - "linkMove"
 * - "move"
 * - "all"
 * - "uninitialized"
 *
 * The default is "all".
 *
 * \see https://developer.mozilla.org/en-US/docs/Web/API/DataTransfer/effectAllowed
 */
DndSimulatorDataTransfer.prototype.effectAllowed = "all";

/*!
 * \brief List of files being dragged.
 *
 * This property will remain an empty list when the drag and drop operation
 * does not involve any files.
 *
 * \see https://developer.mozilla.org/en-US/docs/Web/API/DataTransfer/files
 */
DndSimulatorDataTransfer.prototype.files = [];

DndSimulatorDataTransfer.prototype.items = [];

DndSimulatorDataTransfer.prototype.types = [];

DndSimulatorDataTransfer.prototype.clearData = function(format) {
    if(format) {
        delete this.data[format];

        var index = this.types.indexOf(format);
        delete this.types[index];
        delete this.data[index];
    } else {
        this.data = {};
    }
};

DndSimulatorDataTransfer.prototype.setData = function(format, data) {
    this.data[format] = data;
    this.items.push(data);
    this.types.push(format);
};

DndSimulatorDataTransfer.prototype.getData = function(format) {
    if(format in this.data) {
        return this.data[format];
    }

    return "";
};


DndSimulatorDataTransfer.prototype.setDragImage = function(img, xOffset, yOffset) {
};

DndSimulator = {
    /*!
     * \brief Simulates dragging one element on top of the other.
     *
     * Specified elements can be CSS selectors.
     *
     * \param sourceElement The element to drag to the target element.
     * \param targetElement The element the source element should be
     *                        dragged to.
     */
    simulate: function(sourceElement, targetElement) {
        /* if strings are specified, assume they are CSS selectors */
        if(typeof sourceElement == "string") {
            sourceElement = document.querySelector(sourceElement);
        }

        if(typeof targetElement == "string") {
            targetElement = document.querySelector(targetElement);
        }

        /* get the coordinates of both elements, note that
        left refers to X, and top to Y */
        var sourceCoordinates = sourceElement.getBoundingClientRect();
        var targetCoordinates = targetElement.getBoundingClientRect();

        /* simulate a mouse down event on the coordinates
        of the source element */
        var mouseDownEvent = this.createEvent(
            "mousedown",
            {
                clientX: sourceCoordinates.left,
                clientY: sourceCoordinates.top
            }
        );

        sourceElement.dispatchEvent(mouseDownEvent);

        /* simulate a drag start event on the source element */
        var dragStartEvent = this.createEvent(
            "dragstart",
            {
                clientX: sourceCoordinates.left,
                clientY: sourceCoordinates.top,
                dataTransfer: new DndSimulatorDataTransfer()
            }
        );

        sourceElement.dispatchEvent(dragStartEvent);

        /* simulate a drag event on the source element */
        var dragEvent = this.createEvent(
            "drag",
            {
                clientX: sourceCoordinates.left,
                clientY: sourceCoordinates.top
            }
        );

        sourceElement.dispatchEvent(dragEvent);

        /* simulate a drag enter event on the target element */
        var dragEnterEvent = this.createEvent(
            "dragenter",
            {
                clientX: targetCoordinates.left,
                clientY: targetCoordinates.top,
                dataTransfer: dragStartEvent.dataTransfer
            }
        );

        targetElement.dispatchEvent(dragEnterEvent);

        /* simulate a drag over event on the target element */
        var dragOverEvent = this.createEvent(
            "dragover",
            {
                clientX: targetCoordinates.left,
                clientY: targetCoordinates.top,
                dataTransfer: dragStartEvent.dataTransfer
            }
        );

        targetElement.dispatchEvent(dragOverEvent);

        /* simulate a drop event on the target element */
        var dropEvent = this.createEvent(
            "drop",
            {
                clientX: targetCoordinates.left,
                clientY: targetCoordinates.top,
                dataTransfer: dragStartEvent.dataTransfer
            }
        );

        targetElement.dispatchEvent(dropEvent);

        /* simulate a drag end event on the source element */
        var dragEndEvent = this.createEvent(
            "dragend",
            {
                clientX: targetCoordinates.left,
                clientY: targetCoordinates.top,
                dataTransfer: dragStartEvent.dataTransfer
            }
        );

        sourceElement.dispatchEvent(dragEndEvent);

        /* simulate a mouseup event on the target element */
        var mouseUpEvent = this.createEvent(
            "mouseup",
            {
                clientX: targetCoordinates.left,
                clientY: targetCoordinates.top
            }
        );

        targetElement.dispatchEvent(mouseUpEvent);
    },

    /*!
     * \brief Creates a new fake event ready to be dispatched.
     *
     * \param eventName The type of event to create.
     *                    For example: "mousedown".
     * \param options    Dictionary of options for this event.
     *
     * \returns An event ready for dispatching.
     */
    createEvent: function(eventName, options) {
        var event = document.createEvent("CustomEvent");
        event.initCustomEvent(eventName, true, true, null);

        event.view = window;
        event.detail = 0;
        event.ctlrKey = false;
        event.altKey = false;
        event.shiftKey = false;
        event.metaKey = false;
        event.button = 0;
        event.relatedTarget = null;

        /* if the clientX and clientY options are specified,
        also calculated the desired screenX and screenY values */
        if(options.clientX && options.clientY) {
            event.screenX = window.screenX + options.clientX;
            event.screenY = window.screenY + options.clientY;
        }

        /* copy the rest of the options into
        the event object */
        for (var prop in options) {
            event[prop] = options[prop];
        }

        return event;
    }
};