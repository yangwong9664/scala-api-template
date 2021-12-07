#!/bin/bash
sbt clean coverage scalastyle test it:test dependencyUpdates coverageReport
