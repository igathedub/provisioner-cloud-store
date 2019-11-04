import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { NodeService } from 'app/entities/node/node.service';
import { INode, Node } from 'app/shared/model/node.model';

describe('Service Tests', () => {
  describe('Node Service', () => {
    let injector: TestBed;
    let service: NodeService;
    let httpMock: HttpTestingController;
    let elemDefault: INode;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(NodeService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Node(
        0,
        'AAAAAAA',
        false,
        0,
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Node', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Node(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Node', () => {
        const returnedFromService = Object.assign(
          {
            unicastAddress: 'BBBBBB',
            configComplete: true,
            defaultTTL: 1,
            cid: 'BBBBBB',
            blacklisted: true,
            uUID: 'BBBBBB',
            security: 'BBBBBB',
            crpl: 'BBBBBB',
            name: 'BBBBBB',
            deviceKey: 'BBBBBB',
            vid: 'BBBBBB',
            pid: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Node', () => {
        const returnedFromService = Object.assign(
          {
            unicastAddress: 'BBBBBB',
            configComplete: true,
            defaultTTL: 1,
            cid: 'BBBBBB',
            blacklisted: true,
            uUID: 'BBBBBB',
            security: 'BBBBBB',
            crpl: 'BBBBBB',
            name: 'BBBBBB',
            deviceKey: 'BBBBBB',
            vid: 'BBBBBB',
            pid: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Node', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
